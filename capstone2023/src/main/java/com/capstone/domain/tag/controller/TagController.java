package com.capstone.domain.tag.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capstone.domain.tag.service.TagService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {
	
	private final TagService tagService;

	  @GetMapping("/json")
	  public ResponseEntity<List<Object>> getTagJson() {
	       List<Object> list = this.tagService.getTagJson();
	       return new ResponseEntity<>(list, HttpStatus.OK);
	    }
}
 