package com.capstone.domain.userFile.mapper;

import com.capstone.domain.post.dto.PostRequest;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.file.entity.File;
import com.capstone.domain.userFile.entity.UserFile;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFileMapper {
    public UserFile toEntity(File file, User user, String category ) {
        return UserFile.builder()
                .category(category)
                .user(user)
                .file(file)
                .build();
    }
}
