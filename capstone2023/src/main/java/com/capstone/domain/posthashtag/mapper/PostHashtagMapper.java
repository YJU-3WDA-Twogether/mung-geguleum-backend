package com.capstone.domain.posthashtag.mapper;

import com.capstone.domain.hashtag.entity.Hashtag;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.posthashtag.entity.PostHashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostHashtagMapper {
    public static PostHashtag toEntity(Post post, Hashtag hashtag) {
        return PostHashtag.builder()
                .hashtag(hashtag)
                .post(post)
                .build();
    }
}
