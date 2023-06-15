package com.capstone.domain.hashtag.service;

import com.capstone.domain.hashtag.entity.Hashtag;
import com.capstone.domain.hashtag.mapper.HashtagMapper;
import com.capstone.domain.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class HashtagService {

    private final HashtagMapper hashtagMapper;

    private final HashtagRepository hashtagRepository;
    @Transactional
    public Hashtag hashtagCreate(String content) {
        System.out.println(content);

        List<String> hashtags = new ArrayList<>();

        Pattern pattern = Pattern.compile("#(\\w+)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String hashtag = matcher.group(1);
            hashtags.add(hashtag);
        }
        Hashtag hashtag = hashtagMapper.toEntity(content);
        hashtagRepository.save(hashtag);
        return hashtag;
    }

}
