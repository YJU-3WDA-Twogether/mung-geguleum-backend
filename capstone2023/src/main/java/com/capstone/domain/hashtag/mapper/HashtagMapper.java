package com.capstone.domain.hashtag.mapper;

import com.capstone.domain.hashtag.entity.Hashtag;
import com.capstone.domain.post.dto.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HashtagMapper {

    public List<Hashtag> toEntities(PostRequest postRequest) {
        List<Hashtag> hashtags = new ArrayList<>();
        for (String tag : postRequest.getHashtag()) {
            Hashtag hashtag = Hashtag.builder()
                    .title(tag)
                    .build();
            hashtags.add(hashtag);
        }
        return hashtags;
    }
}
