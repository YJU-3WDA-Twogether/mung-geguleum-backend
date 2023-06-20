package com.capstone.domain.posthashtag.mapper;

import com.capstone.domain.hashtag.entity.Hashtag;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.posthashtag.entity.PostHashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostHashtagMapper {
    public List<PostHashtag> toEntities(Post post, List<Hashtag> hashtags) {
        List<PostHashtag> postHashtags = new ArrayList<>();

        for (Hashtag hashtag : hashtags) {
            PostHashtag postHashtag = PostHashtag.builder()
                    .post(post)
                    .hashtag(hashtag)
                    .build();
            postHashtags.add(postHashtag);
        }

        return postHashtags;
    }

    public PostHashtag toEntity(Post post, Hashtag hashtag) {
        return  PostHashtag.builder()
                    .post(post)
                    .hashtag(hashtag)
                    .build();
    }
}
