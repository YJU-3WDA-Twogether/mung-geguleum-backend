package com.capstone.domain.file.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Builder
@Getter
@Setter
@ToString
@Component
@NoArgsConstructor
@AllArgsConstructor
public class FileRequest {

    private String main;

    private String back;

    private String introduce;

    private String nickname;
}
