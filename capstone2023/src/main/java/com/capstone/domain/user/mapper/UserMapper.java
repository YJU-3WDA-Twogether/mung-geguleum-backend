package com.capstone.domain.user.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.capstone.domain.user.dto.UserCreateForm;
import com.capstone.domain.user.dto.UserDTO;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.entity.UserGrade;

import jakarta.validation.Valid;

@Component
public class UserMapper {
    public User toEntity(@Valid UserCreateForm userCreateForm) {
        return User.builder()
			 	.uid(userCreateForm.getUid())
                .uname(userCreateForm.getUname())
                .password(userCreateForm.getPassword())
                .email(userCreateForm.getEmail())
                .nickname(userCreateForm.getNickname())
              
                .build();
    }
    
    public User toEntity(@Valid UserCreateForm userCreateForm, UserGrade usergrade) {
        return User.builder()
			 	.uid(userCreateForm.getUid())
                .uname(userCreateForm.getUname())
                .password(userCreateForm.getPassword())
                .email(userCreateForm.getEmail())
                .nickname(userCreateForm.getNickname())
                .userGrade(usergrade)
                .regDate(LocalDateTime.now())
                .build();
    }
    public User toEntity(User user, String introduce) { // profile 업데이트
        return User.builder()
                .uid(user.getUid())
                .uname(user.getUname())
                .password(user.getPassword())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .userGrade(user.getUserGrade())
                .regDate(LocalDateTime.now())
                .introduce(introduce)
                .build();
    }
    
    public User toEntity(@Valid UserDTO userDTO) {
    	 return User.builder()
    			 .uno(userDTO.getUno())
 			 	 .uid(userDTO.getUid())
                 .uname(userDTO.getUname())
                 .password(userDTO.getPassword())
                 .email(userDTO.getEmail())
                 .nickname(userDTO.getNickname())
                 .userGrade(new UserGrade())
                 .build();
    }

    public UserCreateForm toUserCreateForm(User User) {
        return UserCreateForm.builder()
                .uid(User.getUid())
                .uname(User.getUname())
                .email(User.getEmail())
                .password(User.getPassword())
                .nickname(User.getNickname())
                .build();
    }
    
    public UserDTO toUserDTO(User User) {
    	return UserDTO.builder()
    			.uno(User.getUno())
    			.uid(User.getUid())
                .uname(User.getUname())
                .email(User.getEmail())
                .password(User.getPassword())
                .nickname(User.getNickname())
                .build();
    };
}

