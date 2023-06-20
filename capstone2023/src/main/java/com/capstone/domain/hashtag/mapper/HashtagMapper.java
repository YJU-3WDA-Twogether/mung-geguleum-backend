package com.capstone.domain.hashtag.mapper;

import com.capstone.domain.hashtag.dto.HashtagResponse;
import com.capstone.domain.hashtag.entity.Hashtag;
import com.capstone.domain.post.dto.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HashtagMapper {
	
	public Hashtag toEntity(String title) {
		Hashtag hashtag = Hashtag.builder()
				.title(title)
				.build();
		return hashtag;
	}
	
	public HashtagResponse toHashTagResponse(Hashtag hashtag) {
		HashtagResponse result = HashtagResponse.builder()
				.htno(hashtag.getHtno())
				.title(hashtag.getTitle())
				.build();
		return result;
	}
}
