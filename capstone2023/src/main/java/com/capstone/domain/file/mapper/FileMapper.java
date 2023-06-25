package com.capstone.domain.file.mapper;

import java.nio.file.Path;
import java.time.LocalDateTime;

import com.capstone.domain.user.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.capstone.domain.file.dto.FileDTO;
import com.capstone.domain.file.entity.File;
import com.capstone.domain.post.entity.Post;

import jakarta.validation.Valid;

@Component
public class FileMapper {

	//Entity에 DTO담기
	public File toEntity(@Valid FileDTO fileDTO, Post post) {
		return File.builder()
				.fno(fileDTO.getFno())
				.fname(fileDTO.getFname())
				.fpath(fileDTO.getFpath())
				.post(post)
				.build();
		
	}
	
	//Entity에 DTO담기
		public File toEntity(@Valid FileDTO fileDTO) {
			return File.builder()
					.fno(fileDTO.getFno())
					.fname(fileDTO.getFname())
					.fpath(fileDTO.getFpath())
					
					.post(null)
					.build();
			
		}

	public  File toEntity(String fname, String fpath ,String ftype, String category, User user) {
		return File.builder()
				.fname(fname)
				.fpath(fpath)
				.ftype(ftype)
				.fcategory(category)
				.user(user)
				.build();

	}
	
	//Entity에 DTO담기
	public  File toEntity(MultipartFile file, Path filePath , LocalDateTime date ,Post post) {
		return File.builder()
				.fname(file.getOriginalFilename())
				.fpath(filePath.toString())
				.post(post)
				.build();
		
	}
	public  File toEntity(String fname, String fpath ,String ftype,Post post) {
		return File.builder()
				.fname(fname)
				.fpath(fpath)
				.ftype(ftype)
				.fcategory("피드")
				.post(post)
				.build();
		
	}
	
	
	
	//DTO에 Entity담기
	public FileDTO toFileDTO(File file, Long pno) {
		return FileDTO.builder()
				.fno(file.getFno())
				.fname(file.getFname())
				.fpath(file.getFpath())
				.ftype(file.getFtype())
				.fcategory(file.getFcategory())
				.pno(pno)
				.build();
	}
	
	//DTO에 Entity담기
		public FileDTO toFileDTO(File file, boolean b ) {
			return FileDTO.builder()
					.fno(file.getFno())
					.fname(file.getFname())
					.fpath(file.getFpath())
					.pno(null)
					.build();
		}
	
	//DTO에 Entity담기
		public static FileDTO toFileDTO(MultipartFile file, Path filePath , LocalDateTime date, Long pno) {
			return FileDTO.builder()
					
					.fname(file.getOriginalFilename())	
					.fpath(filePath.toString())
					.pno(pno)
					.build();
		}
}
