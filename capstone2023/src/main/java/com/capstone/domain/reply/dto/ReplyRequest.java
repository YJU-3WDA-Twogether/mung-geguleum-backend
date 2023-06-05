package com.capstone.domain.reply.dto;



import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRequest {
    private Long rno;   //댓글번호

    private String reply; //댓글

    private Long deph;  //댓글 깊이(대댓글)

    private Long pno;   //게시글 번호

    private Long uno;   // 유저 번호

    private Long cno;   // 부모 댓글 번호(대댓글)
}


