package com.capstone.domain.file.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

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
import com.capstone.domain.post.exception.PostForbiddenException;
import com.capstone.domain.post.exception.PostNotFoundException;
import com.capstone.domain.post.repository.PostRepository;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.exception.UserNotFoundException;
import com.capstone.domain.user.repository.UserRepository;

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
   
   @Value("${aws.url}")
   private String Fpath;
   
   @Value("${aws.basic.url}")
   private String basicImage;
   
   @Value("${aws.basic.name}")
   private String basicname;
   
   //파일 업로드 메소드
   @Transactional(rollbackFor = {IOException.class, Exception.class})
   public Boolean uploadFile(String [] files,Post post, LocalDateTime time) throws Exception{
	  // String Fpath = "https://twogether-bucket.s3.ap-northeast-2.amazonaws.com/";
	   
	 	for(String name : files) {
	 		System.out.println(name.toString());
	 		int dotIndex = name.lastIndexOf(".");
	 		String fname = name.substring(0, dotIndex);
	 		String ftype = name.substring(dotIndex);
            File file = fileMapper.toEntity(fname, Fpath+fname, ftype ,post);  
            fileRepository.save(file); 
	 	}
	 	return true;
   }   
   //파일 다운로드메소드        
   @Transactional
   public FileDTO downloadFile(Long fno, Long uno, Long pno ){
       File file = fileRepository.findByFno(fno).orElseThrow( () -> new FileNotFoundException() );
  	 
  	 //파일 다운로드 로그 생성.
  	 Post post= this.postRepository.findByPno(pno).orElseThrow(() -> new PostNotFoundException() );
  	 User user = this.userRepository.findByUno(uno).orElseThrow( () -> new UserNotFoundException()) ;
  	 LocalDateTime time = LocalDateTime.now();
  	 //2L은 파일다운로드를 의미한다.
  	 
  	 LogRequest logRequest = logMapper.toRequestLog(2L, user.getUno(), post.getUser().getUno(), post.getPno(),time);
		 Log log = this.logService.LogCreate(logRequest);

		 return fileMapper.toFileDTO(file,pno );
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

	@Transactional
	public void userUpdate(FileDTO [] files, User user) {
		//String Fpath = "https://twogether-bucket.s3.ap-northeast-2.amazonaws.com/";
		if(!files[0].getFname().equals("기본")) {
				fileRepository.deleteByUno(user.getUno());
			for(FileDTO  file : files) {
				int dotIndex = file.getFname().lastIndexOf(".");
				String fname = file.getFname().substring(0, dotIndex);
				String ftype = file.getFname().substring(dotIndex);
				System.out.println(fname.toString()+" "+ftype.toString());
				File entity = fileMapper.toEntity(fname, Fpath+fname, ftype, file.getFcategory(), user);
				fileRepository.save(entity);
			}
		}
	}
		
		@Transactional
		public void basicImg(User user) {
			//String Fpath = "https://twogether-bucket.s3.ap-northeast-2.amazonaws.com/";
			fileRepository.deleteByUno(user.getUno());
			File file = fileMapper.toEntity(basicImage,basicname,user);
			fileRepository.save(file);
	}

  }