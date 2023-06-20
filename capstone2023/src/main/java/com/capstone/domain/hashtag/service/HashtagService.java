package com.capstone.domain.hashtag.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.domain.hashtag.entity.Hashtag;
import com.capstone.domain.hashtag.mapper.HashtagMapper;
import com.capstone.domain.hashtag.repository.HashtagRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HashtagService {
	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;
	
	 @Transactional
	 public List<Hashtag> hashtagCreate (String [] hashtags) {
		List<Hashtag> list = new ArrayList<>();
		for(String hashtag : hashtags) {
			Hashtag result = hashtagRepository.findByTitle(hashtag);
			 if (result == null ) {
				 result  = hashtagMapper.toEntity(hashtag);
				 hashtagRepository.save(result);
			 } 
			 list.add(result);
		}
		 return list;
	 } 
}
