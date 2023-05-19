package com.capstone.domain.tag.dto;

import java.time.LocalDateTime;

import com.capstone.domain.log.dto.LogResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TagResponse {

	private Long pno;
	private Long uno;
	private String nickname;
	private String title;
	private LocalDateTime regDate;

	
	
}
