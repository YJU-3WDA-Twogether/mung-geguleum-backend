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
                .ano(replyDTO.getAno())
                .comment(replyDTO.getComment())
                .user(user)
                .post(post)
                .deph(replyDTO.getDeph())
                .parentReply(parentReply)
                .build();
    }
    public ReplyDTO toReplyResponse(Reply reply){
        return ReplyDTO.builder()
                .ano(reply.getAno())
                .comment(reply.getComment())
                .deph(reply.getDeph())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
    }
}
