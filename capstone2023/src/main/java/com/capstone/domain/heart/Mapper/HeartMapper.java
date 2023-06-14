package com.capstone.domain.heart.Mapper;

import com.capstone.domain.heart.dto.HeartDTO;
import com.capstone.domain.heart.entity.Heart;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.user.entity.User;
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

    public HeartDTO toHeartDTO(Heart heart) {
        return HeartDTO.builder()
                .pno(heart.getPost().getPno())
                .uno(heart.getUser().getUno())
                .build();
    }
}
