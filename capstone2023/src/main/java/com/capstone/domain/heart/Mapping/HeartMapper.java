package com.capstone.domain.heart.Mapping;

import com.capstone.domain.heart.dto.HeartRequest;
import com.capstone.domain.heart.entity.Heart;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HeartMapper {

    public Heart toEntity(Post post, User user) {
        return Heart.builder()
                .post(post)
                .user(user)
                .build();
    }
}
