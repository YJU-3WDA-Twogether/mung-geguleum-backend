package com.capstone.domain.hashtag.mapper;

import com.capstone.domain.hashtag.entity.Hashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HashtagMapper {

    public Hashtag toEntity(String content) {
        return Hashtag.builder()
                .title(content)
                .build();
    }
}
