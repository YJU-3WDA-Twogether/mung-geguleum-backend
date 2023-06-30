package com.capstone.domain.user.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.capstone.domain.file.dto.FileDTO;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
		
		private Long uno;
		
		private String uname;
		
		private String uid;
		
		private String password;
		
		@Email
		private String email;
		
		private String nickname;
		
		private String grade;
		
		private LocalDateTime regDate;
		
		private String introduce;
		
		private FileDTO [] file; 
		
		private String fpath; 
}