package com.capstone.domain.file.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.capstone.domain.file.dto.FileDTO;
import com.capstone.domain.file.entity.File;
import com.capstone.domain.file.exception.FileNotFoundException;
import com.capstone.domain.file.mapper.FileMapper;
import com.capstone.domain.file.respository.FileRepository;
import com.capstone.domain.log.dto.LogRequest;
import com.capstone.domain.log.entity.Log;
import com.capstone.domain.log.mapper.LogMapper;
import com.capstone.domain.log.service.LogService;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.post.repository.PostRepository;
import com.capstone.domain.post.exception.*;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.repository.UserRepository;
import com.capstone.domain.user.exception.*;

import lombok.RequiredArgsConstructor;




@Service
@RequiredArgsConstructor
public class FileService {
	private final LogService logService;
	private final UserRepository userRepository;
	private final FileRepository fileRepository;
	private final PostRepository postRepository;
	private final FileMapper fileMapper;
	private final LogMapper logMapper;
    

   @Value("${file.dir}")
    private String fileDir;

   
   //파일 업로드 메소드
   @Transactional(rollbackFor = {IOException.class, Exception.class})
   public Boolean uploadFile(MultipartFile [] files,Post post, LocalDateTime time) throws Exception{
	   File file;
	   FileDTO fileDTO;
	 	for(MultipartFile filetmp : files) {
   			//원래파일 이름 추출
   			String fname = filetmp.getOriginalFilename();
   			//파일이름으로 쓸 uuid 생성
   			String uuid = UUID.randomUUID().toString();
   			//확장자 추출(ex .png)
   			String extension = fname.substring(fname.lastIndexOf("."));
   			
   			String fsname = uuid + extension;
   			
   			String fpath = fileDir + fsname;
   			Long fsize = filetmp.getSize();
         
           
            file = fileMapper.toEntity(fname, fsname,fsize,fpath, time, post);
   		
            filetmp.transferTo(new java.io.File(fpath));
            
            fileRepository.save(file);
             
	 	}
	 	return true;
   }   
   //파일 다운로드메소드        
   @Transactional(rollbackFor = MalformedURLException.class)
   public ResponseEntity<Resource> downloadFile(Long fno, Long uno, Long pno ) throws MalformedURLException{
       File file = fileRepository.findByFno(fno).orElseThrow( () -> new FileNotFoundException() );
       Path filePath = Paths.get(file.getFpath());
       
       UrlResource resource = new UrlResource("file:" + file.getFpath());
  	 String encodedFileName = UriUtils.encode(file.getFname(), StandardCharsets.UTF_8);
  	 String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";
  	 
  	 //파일 다운로드 로그 생성.
  	 Post post= this.postRepository.findByPno(pno).orElseThrow(() -> new PostNotFoundException() );
  	 User user = this.userRepository.findByUno(uno).orElseThrow( () -> new UserNotFoundException()) ;
  	 LocalDateTime time = LocalDateTime.now();
  	 //2L은 파일다운로드를 의미한다.
  	 
  	 LogRequest logRequest = logMapper.toRequestLog(2L, user.getUno(), post.getUser().getUno(), post.getPno(),time);
  	 System.out.println("로그리퀘스트 제대로 저장됨?" +logRequest.toString());
		 Log log = this.logService.LogCreate(logRequest);
		 
  	 return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);
    }
    
    //파일 단일 조회메소드 
   @Transactional(rollbackFor = MalformedURLException.class)
    public UrlResource readFile(Long fno) throws MalformedURLException {
    	File file = fileRepository.findByFno(fno).orElseThrow(() -> new FileNotFoundException() );
		return new UrlResource("file:" + file.getFpath());
    }
   
   @Transactional
    public void deleteFile(Long fno,Long uno) {
   	 File file = fileRepository.findByFno(fno).orElseThrow(() -> new FileNotFoundException() );
   	 if(!file.getPost().getUser().getUno().equals(uno)) {
   		 throw new PostForbiddenException();
   	 }
	 this.fileRepository.deleteById(fno); 
    }


  }