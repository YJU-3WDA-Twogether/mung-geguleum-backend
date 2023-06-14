package com.capstone.domain.postSource.mapper;

import org.springframework.stereotype.Component;

import com.capstone.domain.log.entity.Log;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.postSource.dto.PostSourceResponse;
import com.capstone.domain.postSource.entity.PostSource;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostSourceMapper {
	
	public PostSource toEntity(Post post, Post parentPost) {
		return PostSource.builder()
				.post(post)
				.parentPost(parentPost)
				.build();
	}
	
	public PostSourceResponse toPostSourceResponse(Post postSource) {
		return PostSourceResponse.builder()
				.pno(postSource.getPno())
				.uno(postSource.getUser().getUno())
				.nickname(postSource.getUser().getNickname())
				.title(postSource.getTitle())
				.regDate(postSource.getRegDate())
				.build();
	}
	

}
