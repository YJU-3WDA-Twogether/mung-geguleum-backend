package com.capstone.domain.tag.mapper;

import org.springframework.stereotype.Component;

import com.capstone.domain.log.entity.Log;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.tag.dto.TagResponse;
import com.capstone.domain.tag.entity.Tag;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TagMapper {
	
	public Tag toEntity(Post post, Log log) {
		return Tag.builder()
				.post(post)
				.log(log)
				.build();
	}
	
	public TagResponse toTagResponse(Tag tag) {
		return TagResponse.builder()
				.pno(tag.getPost().getPno())
				.uno(tag.getPost().getUser().getUno())
				.nickname(tag.getPost().getUser().getNickname())
				.title(tag.getPost().getTitle())
				.regDate(tag.getPost().getRegDate())
				.build();
	}
	

}
