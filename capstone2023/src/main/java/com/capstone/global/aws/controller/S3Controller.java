package com.capstone.global.aws.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.global.aws.service.S3Service;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
public class S3Controller {
	
	private final S3Service s3Service;
	private String path;

	@GetMapping("/s3/url")
    public Map<String, Object> getPresignedUrl(@RequestParam Long size ) {
		System.out.println("여기옵니까?"+size);
		
        path = "twogether";
        return s3Service.getPreSignedUrl(size);
    }//
	
//	 @GetMapping("/csv_download/{fileName}")
//	    public ResponseEntity<byte[]> download(@PathVariable String fileName) throws IOException {
//	        return s3Service.getObject(fileName);
//	    }
	
}
