package com.capstone.domain.post.controller;

import java.io.IOException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstone.domain.post.dto.PostRequest;
import com.capstone.domain.post.dto.PostResponse;
import com.capstone.domain.post.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
	
	private final PostService postService;
	
	@GetMapping("/getlist")
	public ResponseEntity<Page> getList(@RequestParam(value="page", defaultValue="0") int page) {
		Page<PostResponse> paging = this.postService.getList(page);
		return ResponseEntity.ok(paging);	
	}
	
	//특정 게시판의 게시글 전체조회 (ex: 음악 게시판의 모든 게시글조회)
	@GetMapping("/getlist/{bname}")
	public ResponseEntity<Page> getList(@RequestParam(value="page", defaultValue="0") int page, @PathVariable String bname) {
		Page<PostResponse> paging = this.postService.getList(page, bname);
		return ResponseEntity.ok(paging);
	}
	
	@PostMapping("/create")
	public  ResponseEntity<Boolean> postCreate( @Valid PostRequest postRequest) throws Exception{
		postService.postCreate(postRequest);
		return ResponseEntity.ok(true);
	}
	//게시글 디테일 조회
	
	@GetMapping("/read/{pno}")
	public ResponseEntity<PostResponse> postRead(@PathVariable Long pno){
		PostResponse result = postService.postRead(pno);
		return ResponseEntity.ok(result);
	} 
	//게시글 업데이트 메소드
	@PutMapping("/update/{pno}")
	public  ResponseEntity<Boolean> postUpdate(@PathVariable Long pno, @Valid PostRequest postDTO){
		PostResponse updatePost = this.postService.postUpdate(pno , postDTO);
		return ResponseEntity.ok(true);		 
	}
	
	//게시글 삭제 
		@DeleteMapping("/delete/{pno}")
		public ResponseEntity<Boolean> postDelete(@PathVariable Long pno){
			this.postService.postDelete(pno);
			return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
		}

	

		
	
}
