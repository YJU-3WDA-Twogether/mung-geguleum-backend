package com.capstone.domain.postSource.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capstone.domain.postSource.service.PostSourceService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/postSource")
public class PostSourceController {
	
	private final PostSourceService tagService;

	  @GetMapping("/getlist")
	  public ResponseEntity<List<Object>> getList() {
	       List<Object> list = this.tagService.getPostSource();
	       return new ResponseEntity<>(list, HttpStatus.OK);
	   }
	  @GetMapping("/getlist/{pno}")
	  public ResponseEntity<List<Object>> getList(@PathVariable Long pno) {
		  System.out.println("hhhhhhhhhhhhh");
	       List<Object> list = this.tagService.getPostSource(pno);
	       
	       return new ResponseEntity<>(list, HttpStatus.OK);
	   } 
}
 