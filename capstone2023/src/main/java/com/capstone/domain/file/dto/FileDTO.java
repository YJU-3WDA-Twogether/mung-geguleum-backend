package com.capstone.domain.file.dto;

import java.time.LocalDateTime;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

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
public class FileDTO {
	
	private Long fno;
	private String fname;
	private String fpath;
	private String ftype;
	private String fcategory;
	private Long uno;
	private Long pno;
	
}
