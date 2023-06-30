package com.capstone.domain.log.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.capstone.domain.log.dto.LogResponse;
import com.capstone.domain.log.service.LogService;
import com.capstone.global.security.jwt.JwtAuthentication;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/log")
public class LogController {
	private final LogService logService;
	
	//달력로그 확인하는 메소드. 
	@GetMapping("/getlist")
	public ResponseEntity<List> getList(@RequestParam String date, @RequestParam Long uno , @RequestParam(defaultValue = "0") int page) {
		System.out.println(date + " " +uno);
		List<LogResponse> paging = this.logService.getList(date, uno );
		return ResponseEntity.ok(paging);
	}
	
	

	//다운로드 로그 확인하는 메소드 필요함.
	@GetMapping("/getdownlist")
	public ResponseEntity<Page> getDownList (@AuthenticationPrincipal JwtAuthentication user, @RequestParam(defaultValue = "0") int page){
		Page<LogResponse> paging = this.logService.getDownList(user.uno, page);
		return ResponseEntity.ok(paging);
	}
	
	//재창작태그 삽입하는 메소드 필요함
	@GetMapping("/getpostsourcelist")
	public ResponseEntity<Page> getTagList (@AuthenticationPrincipal JwtAuthentication user, @RequestParam(defaultValue = "0") int page){
		System.out.println("태그리스트 실행됨.");
		Page<LogResponse> paging = this.logService.getPostSourceList(user.uno, page);
		return ResponseEntity.ok(paging);
	}
	
	@PostMapping("/report/{pno}")
	public ResponseEntity<Boolean> reportPost(@PathVariable Long pno, @AuthenticationPrincipal JwtAuthentication user ){
		System.out.println("신고한 게시글 : "+pno +" 신고한 사람"+user.uno);
		boolean result = logService.reportPost(pno,user.uno);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/getreportlist")
	public ResponseEntity<Page> getReportList (@AuthenticationPrincipal JwtAuthentication user, @RequestParam(defaultValue = "0") int page){
		Page<LogResponse> paging = this.logService.getReportList(page);
		return ResponseEntity.ok(paging);
	}
	
}
