package com.capstone.domain.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.capstone.domain.file.dto.FileDTO;
import com.capstone.domain.heart.dto.HeartDTO;
import com.capstone.domain.posthashtag.dto.PostHashtagResponse;
import com.capstone.domain.reply.dto.ReplyResponse;

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
public class PostResponse {
	private Long pno;
	
	private String title;
	
	private String content;

	private LocalDateTime regDate;
	
	private LocalDateTime modDate;
	
	private String bname;
	
	private String uid;
	
	private Long uno;

	private Long rCount;

	private Long hCount;

	private Long lCount;

	private Boolean hExist;

	List <FileDTO> file;
	
	//추후 댓글 DTO 담아서 저장한다음에 보내면됩니다.
	List <ReplyResponse> reply;

	List <HeartDTO> heart;
	
	List<PostHashtagResponse> postHashtag;
	
}
