package com.capstone.domain.post.controller;

import java.io.IOException;
import java.util.List;

import com.capstone.domain.hashtag.entity.Hashtag;
import com.capstone.domain.hashtag.service.HashtagService;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.posthashtag.service.PostHashtagService;
import com.capstone.global.security.jwt.JwtAuthentication;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
	private final HashtagService hashtagService;
	private final PostHashtagService postHashtagService;
	@GetMapping("/getlist")
	public ResponseEntity<Page> getList(@RequestParam(value="page", defaultValue="0") int page, @AuthenticationPrincipal JwtAuthentication user) {
		Page<PostResponse> paging = this.postService.getList(page, user.uno);
		return ResponseEntity.ok(paging);	
	}
	
	//특정 게시판의 게시글 전체조회 (ex: 음악 게시판의 모든 게시글조회)
	@GetMapping("/getlist/{bname}")
	public ResponseEntity<Page> getList(@RequestParam(value="page", defaultValue="0") int page, @PathVariable String bname, @AuthenticationPrincipal JwtAuthentication user) {
		Page<PostResponse> paging = this.postService.getList(page, bname, user.uno);;
		return ResponseEntity.ok(paging);
	}
	
	@PostMapping("/create")
	public  ResponseEntity<Boolean> postCreate(@RequestBody PostRequest postRequest, @AuthenticationPrincipal JwtAuthentication user) throws Exception{
		System.out.println("컨트롤러 진입");
		Post post = postService.postCreate(postRequest, user.uno);
		System.out.println("post service 진입");
		Hashtag hashtag = hashtagService.hashtagCreate(postRequest.getContent());
		System.out.println("hashtag service 진입");
//		postHashtagService.postHashtagCreate(post, hashtag);
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
	public  ResponseEntity<Boolean> postUpdate(@PathVariable Long pno, @Valid PostRequest postDTO, @AuthenticationPrincipal JwtAuthentication user){
		PostResponse updatePost = this.postService.postUpdate(pno , postDTO, user.uno);
		return ResponseEntity.ok(true);		 
	}
	
	//게시글 삭제 
		@DeleteMapping("/delete/{pno}")
		public ResponseEntity<Boolean> postDelete(@PathVariable Long pno ,@AuthenticationPrincipal JwtAuthentication user){
			this.postService.postDelete(pno, user.uno);
			return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
		}

	//내가 쓴 게시글 조회
	@GetMapping("/getMyPost/{uno}")
	public ResponseEntity<Page> getMyPost(@RequestParam(value="page", defaultValue="0") int page, @PathVariable Long uno){
		Page<PostResponse> paging = this.postService.getMyPost(page, uno);
		return ResponseEntity.ok(paging);
	}




}
