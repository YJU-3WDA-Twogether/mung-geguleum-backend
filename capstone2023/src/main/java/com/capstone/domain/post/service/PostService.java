package com.capstone.domain.post.service;



import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.capstone.domain.hashtag.entity.Hashtag;
import com.capstone.domain.hashtag.mapper.HashtagMapper;
import com.capstone.domain.hashtag.repository.HashtagRepository;
import com.capstone.domain.posthashtag.entity.PostHashtag;
import com.capstone.domain.posthashtag.mapper.PostHashtagMapper;
import com.capstone.domain.posthashtag.repository.PostHashtagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.domain.board.entity.Board;
import com.capstone.domain.board.exception.BoardNotFoundException;
import com.capstone.domain.board.repository.BoardRepository;
import com.capstone.domain.file.dto.FileDTO;
import com.capstone.domain.file.mapper.FileMapper;
import com.capstone.domain.file.service.FileService;
import com.capstone.domain.log.dto.LogRequest;
import com.capstone.domain.log.entity.Log;
import com.capstone.domain.log.mapper.LogMapper;
import com.capstone.domain.log.service.LogService;
import com.capstone.domain.post.dto.PostRequest;
import com.capstone.domain.post.dto.PostResponse;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.post.exception.PostForbiddenException;
import com.capstone.domain.post.exception.PostNotFoundException;
import com.capstone.domain.post.mapper.PostMapper;
import com.capstone.domain.post.repository.PostRepository;
import com.capstone.domain.postSource.service.PostSourceService;
import com.capstone.domain.reply.dto.ReplyResponse;
import com.capstone.domain.reply.mapper.ReplyMapper;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.exception.UserNotFoundException;
import com.capstone.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final BoardRepository boardRepository;
	private final HashtagRepository hashtagRepository;
	private final PostHashtagRepository postHashtagRepository;

	private final FileService fileService;
	private final LogService logService;
	private final PostSourceService tagService;
	
	private final PostMapper postMapper;
	private final LogMapper logMapper;
	private final FileMapper fileMapper;
    private final ReplyMapper replyMapper;
	private final HashtagMapper hashtagMapper;
	private final PostHashtagMapper postHashtagMapper;
	//게시판 생성 메소드
	@Transactional(rollbackFor = {Exception.class, IOException.class})
	public void postCreate(PostRequest postRequest, Long uno) throws Exception{
		LocalDateTime time = LocalDateTime.now();
		postRequest.setRegDate(time);
		User user = this.userRepository.findByUno(uno).orElseThrow(()-> new UserNotFoundException ());
		Board board = this.boardRepository.findByBno(postRequest.getBno()).orElseThrow(()-> new BoardNotFoundException ());
		Post post = postMapper.toEntity(postRequest,board ,user);

		post = this.postRepository.save(post);

		/*** Hashtag 저장 ***/
		List<Hashtag> hashtags = hashtagMapper.toEntities(postRequest);
		for (Hashtag hashtag : hashtags) {	// hashtag 값이 db에 없다면 추가
			Hashtag hashtagExist = hashtagRepository.findByTitle(hashtag.getTitle());
			if(hashtagExist == null) {
				this.hashtagRepository.save(hashtag);
			}
		}

		/*** PostHashtag 저장 ***/
		List<PostHashtag> postHashtags = postHashtagMapper.toEntities(post, hashtags);

		for (PostHashtag postHashtag : postHashtags) {
			String hashtagTitle = postHashtag.getHashtag().getTitle();
			Hashtag hashtagCheck = hashtagRepository.findByTitle(hashtagTitle);
			if(hashtagCheck != null) {
				PostHashtag savedPostHashtag = postHashtagMapper.toEntity(post, hashtagCheck);
				this.postHashtagRepository.save(savedPostHashtag);
			}else {
				this.postHashtagRepository.save(postHashtag);
			}
		}

		if(postRequest.getFile()!=null) {
			this.fileService.uploadFile(postRequest.getFile(),post ,time);
		}
		
		//게시글 등록후 로그 발행.
		Long lsno = 1L;
		LogRequest logRequest = logMapper.toRequestLog(lsno, user.getUno(), post.getPno(),time);
		Log log = this.logService.LogCreate(logRequest);
		
		//재창작인 경우 
		
		if(board.getBno()==4&&postRequest.getTag() !=null) {
			tagService.postSourceCreate(postRequest.getTag(),post);
		}
	}
	
	//모든페이지 전체조회함.
	@Transactional
	public Page<PostResponse> getList(int page, Long uno) {
		Pageable pageable = PageRequest.of(page,10);
		Page<Object[]> result = postRepository.findAllWithBoardAndUser(pageable);

	    return result.map(objects -> {
	        Post post = (Post) objects[0];
	        Board board = (Board) objects[1];
	        User user = (User) objects[2];
	        return postMapper.toPostResponse(post,board,user, uno) ;
	    });
	}

	
	//한  종류 게시판 조회
	@Transactional
	public Page<PostResponse> getList(int page, String bname, Long uno){
		 Pageable pageable = PageRequest.of(page, 10, Sort.by("pno").descending());
		Page<Object[]> result = postRepository.findAllByBoardName(bname, pageable);

	    return result.map(objects -> {
	        Post post = (Post) objects[0];
	        Board board = (Board) objects[1];
	        User user = (User) objects[2];
			return postMapper.toPostResponse(post,board,user,uno) ;
	    });
	}
	
	//게시판 디테일 조회하는 메소드
	@Transactional
	public PostResponse postRead(Long pno) {
	    Post post= postRepository.findByFilesAndReply(pno).orElseThrow(()-> new PostNotFoundException());
		List<FileDTO> fileDTOList = post.getFiles().stream()
    	        .map(file -> fileMapper.toFileDTO(file, pno))
    	        .collect(Collectors.toList());

        List<ReplyResponse> replyDTOList = post.getReplys().stream()
				.map(reply -> replyMapper.toReplyDTO(reply, pno))
				.collect(Collectors.toList());

	   return postMapper.toPostResponse(post, fileDTOList, replyDTOList);
	}
	
	

	//게시판 수정 메소드
	@Transactional
	public PostResponse postUpdate(Long pno, PostRequest postDTO, Long uno) {
			Post post= this.postRepository.findByPno(pno).orElseThrow(() -> new PostNotFoundException()) ;
			//pk값은 존재하고 나머지 값이 null일 경우에 nullpointException을 추가적으로 발행해줘야함.
			User user = this.userRepository.findByUno(uno).orElseThrow(() -> new UserNotFoundException());
			Board board = this.boardRepository.findByBno(postDTO.getBno()).orElseThrow( () -> new BoardNotFoundException());
			if(!post.getUser().getUno().equals(user.getUno())) {
				System.out.println("post : "+ post.getUser().getUno()+" user : " + user.getUno());
				throw new PostForbiddenException();
			}
			post = postMapper.toEntity(postDTO ,board ,user);

		this.postHashtagRepository.deleteByPno(pno);	// PostHashtag 삭제 기능

		/*** Hashtag 저장 ***/
		List<Hashtag> hashtags = hashtagMapper.toEntities(postDTO);
		for (Hashtag hashtag : hashtags) {	// hashtag 값이 db에 없다면 추가
			Hashtag hashtagExist = hashtagRepository.findByTitle(hashtag.getTitle());
			if(hashtagExist == null) {
				this.hashtagRepository.save(hashtag);
			}
		}

		/*** PostHashtag 저장 ***/
		List<PostHashtag> postHashtags = postHashtagMapper.toEntities(post, hashtags);

		for (PostHashtag postHashtag : postHashtags) {
			String hashtagTitle = postHashtag.getHashtag().getTitle();
			Hashtag hashtagCheck = hashtagRepository.findByTitle(hashtagTitle);
			if(hashtagCheck != null) {
				PostHashtag savedPostHashtag = postHashtagMapper.toEntity(post, hashtagCheck);
				this.postHashtagRepository.save(savedPostHashtag);
			}else {
				this.postHashtagRepository.save(postHashtag);
			}
		}

			return postMapper.toPostResponse(this.postRepository.save(post));
	}
	
	
	//게시판 삭제메소드
	@Transactional
	public void postDelete(Long pno, Long uno) {
		Post post= this.postRepository.findByPno(pno).orElseThrow(() -> new PostNotFoundException()) ;
		User user = this.userRepository.findByUno(uno).orElseThrow(() -> new UserNotFoundException());
		if(!post.getUser().getUno().equals(user.getUno()))
			throw new PostForbiddenException();

		this.postHashtagRepository.deleteByPno(pno);

		//게시글을 삭제 할 수 있는 기능 추가 해야함!
	}
		
	// 내가 쓴 게시글 메소드
	@Transactional
	public Page<PostResponse> getMyPost(int page, Long uno) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("pno").descending());
		Page<Object[]> result = postRepository.findMyPost(pageable, uno);
		return result.map(objects -> {
			Post post = (Post) objects[0];
			Board board = (Board) objects[1];
			User user = (User) objects[2];
			return postMapper.toPostResponse(post,board,user) ;
		});
	}

	
}
