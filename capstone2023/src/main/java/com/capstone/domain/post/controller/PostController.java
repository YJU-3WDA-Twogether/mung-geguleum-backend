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
@Slf4j
public class PostController {
	
	private final PostService postService;
	
	@ResponseBody
	@GetMapping("/getlist")
	public ResponseEntity<Page> getList(@RequestParam(value="page", defaultValue="0") int page) {
		System.out.println("누군가 게시판 전체조회를 시도했다.");
		Page<PostResponse> paging = this.postService.getList(page);
		
		if(paging != null) {
			System.out.println("게시판 전체조회 성공");
			return ResponseEntity.ok(paging);	
		}else {
			System.out.println("게시판 전체조회 실패");
			return new ResponseEntity<>(paging, HttpStatus.BAD_REQUEST);
		}
	}
	
	//특정 게시판의 게시글 전체조회 (ex: 음악 게시판의 모든 게시글조회)
	@ResponseBody
	@GetMapping("/getlist/{bname}")
	public ResponseEntity<Page> getList(@RequestParam(value="page", defaultValue="0") int page,@PathVariable String bname) {
		Page<PostResponse> paging = this.postService.getList(page, bname);
		return ResponseEntity.ok(paging);
//		if(paging != null) {
//			System.out.println("게시판 전체조회 성공");
//			return ResponseEntity.ok(paging);	
//		}else {
//			System.out.println("게시판 전체조회 실패");
//			return new ResponseEntity<>(paging, HttpStatus.BAD_REQUEST);
//		}
	}
	
	@PostMapping("/create")
	//@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public  ResponseEntity<Boolean> postCreate( @Valid PostRequest postRequest , BindingResult bindingResult) throws IOException {
		postService.postCreate(postRequest);
		return ResponseEntity.ok(true);
//		System.out.println("누군가 게시판 작성을 시도했다.");
//		if(postRequest.getTag() != null) {
//			for(Long t : postRequest.getTag()) {
//				System.out.println(t + " 태그테스트");
//			}
//		}else {
//			System.out.println("태그 안에 없다.");
//		}
//		if(bindingResult.hasErrors()) {	
//			System.out.println("게시판 작성 실패했다.");
//			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
//		}
//		try {
//			
//			postService.postCreate(postRequest);
//			
//		}catch(DataIntegrityViolationException e) {
//			e.printStackTrace();
//			bindingResult.reject("creapostFailed", "게시판 등록에 실패했습니다.");
//			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
//
//		}catch(Exception e) {
//			e.printStackTrace();
//			bindingResult.reject("creapostFailed", e.getMessage());
//			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
//		}
//		 return ResponseEntity.ok(true);
	}
	//게시글 디테일 조회
	
	@GetMapping("/read/{pno}")
	public ResponseEntity<PostResponse> postRead(@PathVariable Long pno){
		PostResponse result = postService.postRead(pno);
		return new ResponseEntity<>(result, HttpStatus.OK);
	} 
	//게시글 업데이트 메소드
	@PutMapping("/update/{pno}")
	public  ResponseEntity<Boolean> postUpdate(@PathVariable Long pno, @Valid PostRequest postDTO , BindingResult bindingResult){
		PostResponse updatePost = this.postService.postUpdate(pno , postDTO);
		return ResponseEntity.ok(true);
//		System.out.println("누군가 게시글 수정을 시도했다.");
//		if(bindingResult.hasErrors()) {
//			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
//		}
//		
//		PostResponse updatePost;
//		
//		try {
//			updatePost = this.postService.postUpdate(pno , postDTO);
//		}catch(DataIntegrityViolationException e) {
//			e.printStackTrace();
//			bindingResult.reject("UpdateFailed", "오류가 발생 했습니다.");
//			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
//
//		}catch(Exception e) {
//			e.printStackTrace();
//			bindingResult.reject("UpdateFailed", e.getMessage());
//			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
//		}
//		if(updatePost == null ) {
//			return ResponseEntity.notFound().build();
//		}
		
		 
	}
	
	//게시글 삭제 
		@DeleteMapping("/delete/{pno}")
		public ResponseEntity<Boolean> postDelete(@PathVariable Long pno){
			this.postService.postDelete(pno);
			return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
//			try {
//				this.postService.postDelete(pno);
//			}catch(Exception e){
//				e.printStackTrace();
//				return new ResponseEntity<>(false , HttpStatus.NO_CONTENT);
//				
//			}
//			
//			return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
		}

	

		
	
}
