package com.capstone.domain.posthashtag.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capstone.domain.hashtag.entity.Hashtag;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.posthashtag.entity.PostHashtag;
import com.capstone.domain.posthashtag.mapper.PostHashtagMapper;
import com.capstone.domain.posthashtag.repository.PostHashtagRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostHashtagService {
	
    private final PostHashtagRepository postHashtagRepository;
    private final PostHashtagMapper postHashtagMapper;
   
    public void postHashTagCreate (Post post, List<Hashtag> hashTags) {
    	for(Hashtag hashtag : hashTags) {
    		PostHashtag posthashtag = postHashtagMapper.toEntity(post, hashtag);
    		postHashtagRepository.save(posthashtag);
    	}
    }
}
