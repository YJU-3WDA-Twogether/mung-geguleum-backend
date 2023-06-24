package com.capstone.domain.post.service;



import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.domain.board.entity.Board;
import com.capstone.domain.board.exception.BoardNotFoundException;
import com.capstone.domain.board.repository.BoardRepository;
import com.capstone.domain.file.mapper.FileMapper;
import com.capstone.domain.file.respository.FileRepository;
import com.capstone.domain.file.service.FileService;
import com.capstone.domain.hashtag.entity.Hashtag;
import com.capstone.domain.hashtag.service.HashtagService;
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
import com.capstone.domain.posthashtag.repository.PostHashtagRepository;
import com.capstone.domain.posthashtag.service.PostHashtagService;
import com.capstone.domain.reply.mapper.ReplyMapper;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.exception.UserInvalidException;
import com.capstone.domain.user.exception.UserNotFoundException;
import com.capstone.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
	
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final BoardRepository boardRepository;
	private final PostHashtagRepository postHashtagRepository;
	private final FileRepository fileRepository;

	private final FileService fileService;
	private final LogService logService;
	private final PostSourceService tagService;
	private final HashtagService hashtagService;
	private final PostHashtagService postHashTagService;
	
	private final PostMapper postMapper;
	private final LogMapper logMapper;
	
	
	
	
	//게시판 생성 메소드
	@Transactional(rollbackFor = {Exception.class, IOException.class})
	public void postCreate(PostRequest postRequest, Long uno) throws Exception{
		LocalDateTime time = LocalDateTime.now();
		postRequest.setRegDate(time);
		User user = this.userRepository.findByUno(uno).orElseThrow(()-> new UserNotFoundException ());
		Board board = this.boardRepository.findByBno(postRequest.getBno()).orElseThrow(()-> new BoardNotFoundException ());
		Post post = postMapper.toEntity(postRequest , board ,user);
		
		post = this.postRepository.save(post);
		
		if(postRequest.getHashtag()!=null) {
			//HashTag를 저장하는 코드다. 
			List<Hashtag> hashtags = hashtagService.hashtagCreate(postRequest.getHashtag());
			//PostHashtag 저장
			postHashTagService.postHashTagCreate(post, hashtags);
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
			System.out.println("재창작입니다.========================================");
			tagService.postSourceCreate(postRequest.getTag(),post);
		}	
	}
	
	//모든페이지 전체조회함.
	@Transactional
	public Page<PostResponse> getList(int page, Long uno) {
		Pageable pageable = PageRequest.of(page,100);
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
		 Pageable pageable = PageRequest.of(page, 100, Sort.by("pno").descending());
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
	    Post post = postRepository.findByFilesAndReply(pno).orElseThrow(()-> new PostNotFoundException());
	    User user = userRepository.findById(post.getUser().getUno()).orElseThrow(()-> new UserNotFoundException() ); 
	   return postMapper.toPostResponse(post);
	}
	
	

	//게시판 수정 메소드
	@Transactional(rollbackFor = {Exception.class, IOException.class})
	public PostResponse postUpdate(Long pno, PostRequest postRequest, Long uno,String role) throws Exception {
			Post post= this.postRepository.findByPno(pno).orElseThrow(() -> new PostNotFoundException()) ;
			User user = this.userRepository.findByUno(uno).orElseThrow(() -> new UserNotFoundException());
			Board board = this.boardRepository.findByBno(postRequest.getBno()).orElseThrow( () -> new BoardNotFoundException());
			if(role.equals("ADMIN")) {
				if(!user.getUserGrade().getGname().equals("ADMIN")) {
					throw new UserInvalidException();
				}
			}else if(!post.getUser().getUno().equals(user.getUno())) {
				throw new PostForbiddenException();
			}
			
			fileRepository.deleteByPno(post.getPno());
			if(postRequest.getFile()!=null) {
				LocalDateTime time = LocalDateTime.now();
				this.fileService.uploadFile(postRequest.getFile(),post ,time);
			}
			
		this.postHashtagRepository.deleteByPno(pno);	// PostHashtag 삭제 기능
		if(postRequest.getHashtag()!= null) {
			List<Hashtag> hashtags = hashtagService.hashtagCreate(postRequest.getHashtag());
			postHashTagService.postHashTagCreate(post, hashtags);
		}
		
		post = postMapper.toEntity(postRequest ,board ,user);
		
			return postMapper.toPostResponse(this.postRepository.save(post));
	}
	
	
	//게시판 삭제메소드
	@Transactional
	public void postDelete(Long pno, Long uno, String role) {
		Post post= this.postRepository.findByPno(pno).orElseThrow(() -> new PostNotFoundException()) ;
		User user = this.userRepository.findByUno(uno).orElseThrow(() -> new UserNotFoundException());
		if(role.equals("ADMIN")) {
			if(!user.getUserGrade().getGname().equals("ADMIN")) {
				throw new UserInvalidException();
			}
		}else if(!post.getUser().getUno().equals(user.getUno()))
			throw new PostForbiddenException();
		this.postRepository.deleteById(pno);
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

	public Page<PostResponse> getSearchPost(int page, String search, Long uno) {
		Pageable pageable = PageRequest.of(page, 10, Sort.by("pno").descending());
		Page<Object[]> result = postRepository.findSearchPost(pageable, search);
		return result.map(objects -> {
			Post post = (Post) objects[0];
			Board board = (Board) objects[1];
			User user = (User) objects[2];
			return postMapper.toPostResponse(post,board,user, uno) ;
		});
	}
	
	
}
