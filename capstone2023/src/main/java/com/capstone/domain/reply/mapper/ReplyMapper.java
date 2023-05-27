package com.capstone.domain.reply.mapper;

import com.capstone.domain.reply.dto.ReplyDTO;
import com.capstone.domain.reply.entity.Reply;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyMapper {

    public Reply toEntity(@Valid ReplyDTO replyDTO, User user, Post post, Reply parentReply){
        return Reply.builder()
                .rno(replyDTO.getRno())
                .comment(replyDTO.getComment())
                .user(user)
                .post(post)
                .deph(replyDTO.getDeph())
                .parentReply(parentReply)
                .build();
    }

    public ReplyDTO toReplyDTO(@Valid Reply reply, Long pno){
        return ReplyDTO.builder()
                .rno(reply.getRno())
                .comment(reply.getComment())
                .deph(reply.getDeph())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .pno(pno)
                .uno(reply.getUser().getUno())
                .cno(reply.getParentReply() != null ? reply.getParentReply().getRno() : null)
                .build();
    }
    public ReplyDTO toReplyResponse(Reply reply){
        return ReplyDTO.builder()
                .rno(reply.getRno())
                .comment(reply.getComment())
                .deph(reply.getDeph())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
    }
}
