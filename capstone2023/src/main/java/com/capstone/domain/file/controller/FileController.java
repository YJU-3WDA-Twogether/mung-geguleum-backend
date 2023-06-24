package com.capstone.domain.file.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstone.domain.file.dto.FileDTO;
import com.capstone.domain.file.service.FileService;
import com.capstone.global.security.jwt.JwtAuthentication;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {
	
	private final FileService fileService;
	
	@GetMapping("/download/{fno}")
	    public ResponseEntity<FileDTO> downloadFile(@PathVariable Long fno,@AuthenticationPrincipal JwtAuthentication user, @RequestParam Long pno) throws MalformedURLException {	
			FileDTO result = this.fileService.downloadFile(fno, user.uno , pno);
	    	return ResponseEntity.ok(result);
	    }
	    
	    //파일 보여주기 메소드
	    @GetMapping("/read/{fno}")
	    @ResponseBody
	    public Resource readFile(@PathVariable("fno") Long fno, Model model) throws IOException{
	    	UrlResource url = this.fileService.readFile(fno);
	        return url;
	    }
	   
	    //파일 삭제 메소드
	    @DeleteMapping("/delete/{fno}")
	    public ResponseEntity<?> deleteFile(@PathVariable Long fno, @AuthenticationPrincipal JwtAuthentication user) {
	        this.fileService.deleteFile(fno,user.uno);
	        return ResponseEntity.ok().build();
	    }
}
