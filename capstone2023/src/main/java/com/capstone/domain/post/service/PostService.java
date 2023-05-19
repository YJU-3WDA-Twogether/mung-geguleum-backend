package com.capstone.domain.post.service;



import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capstone.domain.board.entity.Board;
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
import com.capstone.domain.post.mapper.PostMapper;
import com.capstone.domain.post.repository.PostRepository;
import com.capstone.domain.tag.service.TagService;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final BoardRepository boardRepository;
	private final FileService fileService;
	private final LogService logService;
	private final TagService tagService;
	
	private final PostMapper postMapper;
	private final LogMapper logMapper;
	private final FileMapper fileMapper;
	
	//게시판 생성 메소드
	@Transactional(rollbackOn = {Exception.class})
	public void postCreate(PostRequest postRequest){
		LocalDateTime time = LocalDateTime.now();
		postRequest.setRegDate(time);
		User user = this.userRepository.findByUno(postRequest.getUno()).orElseThrow(()-> new RuntimeException());
		Board board = this.boardRepository.findByBno(postRequest.getBno()).orElseThrow(()-> new RuntimeException());
		Post post = postMapper.toEntity(postRequest,board ,user);
		post = this.postRepository.save(post);
		if(postRequest.getFile()!=null) {
			this.fileService.uploadFile(postRequest.getFile(),post ,time);
		}
		
		//게시글 등록후 로그 발행.
		Long lsno = 1L;
		LogRequest logRequest = logMapper.toRequestLog(lsno, user.getUno(), post.getPno(),time);
		Log log = this.logService.LogCreate(logRequest);
		//재창작이 아닐경우
		if(board.getBno()!=4&&postRequest.getTag() !=null) {
			tagService.tagCreate(postRequest.getTag(),post);
		}	
	}
	
	//모든페이지 전체조회함.
	@Transactional
	public Page<PostResponse> getList(int page) {
		Pageable pageable = PageRequest.of(page,30);
		//Pageable pageable = PageRequest.of(page,10);
		Page<Object[]> result = postRepository.findAllWithBoardAndUser(pageable);
	    return result.map(objects -> {
	        Post post = (Post) objects[0];
	        Board board = (Board) objects[1];
	        User user = (User) objects[2];
	        return postMapper.toPostResponse(post,board,user) ;
	        		
	    });
	}

	
	//한  종류 게시판 조회
	@Transactional
	public Page<PostResponse> getList(int page, String bname){
		//Pageable pageable = PageRequest.of(page,10);
		 Pageable pageable = PageRequest.of(page, 30, Sort.by("pno").descending());
		Page<Object[]> result = postRepository.findAllByBoardName(bname, pageable);
	    return result.map(objects -> {
	        Post post = (Post) objects[0];
	        Board board = (Board) objects[1];
	        User user = (User) objects[2];
	        return postMapper.toPostResponse(post,board,user) ;
	        		
	    });
	}
	
	//게시판 디테일 조회하는 메소드
	@Transactional
	public PostResponse postRead(Long pno) {
	    Post post= postRepository.findByPnoWithFiles(pno).orElseThrow(()-> new RuntimeException());
		List<FileDTO> fileDTOList = post.getFiles().stream()
    	        .map(file -> fileMapper.toFileDTO(file, pno))
    	        .collect(Collectors.toList());
	   return postMapper.toPostResponse(post, fileDTOList);
	}
	
	

	//게시판 수정 메소드
	@Transactional
	public PostResponse postUpdate(Long pno, PostRequest postDTO) {
			Post post= this.postRepository.findByPno(pno).orElseThrow(() -> new RuntimeException()) ;
			User user = this.userRepository.findByUno(postDTO.getUno()).orElseThrow(() -> new RuntimeException());
			Board board = this.boardRepository.findByBno(postDTO.getBno()).orElseThrow( () -> new RuntimeException());		
			Post Post = postMapper.toEntity(postDTO ,board ,user);
			this.postRepository.save(Post);
			return postMapper.toPostResponse(Post);
	}
	
	
	//게시판 삭제메소드
	@Transactional
	public void postDelete(Long pno) {
		this.postRepository.deleteById(pno);
	}
		


	
}
