package com.capstone.domain.log.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstone.domain.log.dto.LogResponse;
import com.capstone.domain.log.service.LogService;
import com.capstone.domain.post.dto.PostResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/log")
public class LogController {
	private final LogService logService;
	
	//달력로그 확인하는 메소드. 
	@GetMapping("/getlist")
	public ResponseEntity<Page> getList(@RequestParam String date, @RequestParam Long uno , @RequestParam(defaultValue = "0") int page) {
		Page<LogResponse> paging = this.logService.getList(page, date, uno );
		return ResponseEntity.ok(paging);
	}
	
	
	
	//다운로드 로그 확인하는 메소드 필요함.
	@GetMapping("/getdownlist")
	public ResponseEntity<Page> getDownList (@RequestParam Long uno, @RequestParam(defaultValue = "0") int page){
		Page<LogResponse> paging = this.logService.getDownList(uno, page);
		return ResponseEntity.ok(paging);
	}
	
	//재창작태그 삽입하는 메소드 필요함
	@GetMapping("/gettaglist")
	public ResponseEntity<Page> getTagList (@RequestParam Long uno, @RequestParam(defaultValue = "0") int page){
		System.out.println("태그리스트 실행됨.");
		Page<LogResponse> paging = this.logService.getTagList(uno, page);
		return ResponseEntity.ok(paging);
	}
	
}
