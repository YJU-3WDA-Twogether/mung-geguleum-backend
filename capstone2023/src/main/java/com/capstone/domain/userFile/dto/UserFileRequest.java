package com.capstone.domain.userFile.dto;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@ToString
@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserFileRequest {

    private Long unfo;

    private Long uno;

    private Long fno;

    private String category;

//    private String nickname;

    private String introduce;

    MultipartFile[] files;


}
