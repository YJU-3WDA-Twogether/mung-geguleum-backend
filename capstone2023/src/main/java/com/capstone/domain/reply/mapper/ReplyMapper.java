package com.capstone.domain.reply.mapper;

import com.capstone.domain.reply.dto.ReplyRequest;
import com.capstone.domain.reply.dto.ReplyResponse;
import com.capstone.domain.reply.entity.Reply;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyMapper {

    public Reply toEntity(@Valid ReplyRequest replyDTO, User user, Post post, Reply parentReply){
        return Reply.builder()
                .rno(replyDTO.getRno())
                .reply(replyDTO.getReply())
                .user(user)
                .post(post)
                .deph(replyDTO.getDeph())
                .parentReply(parentReply)
                .build();
    }

    public ReplyResponse toReplyResponse(@Valid Reply reply, Long pno){
        return ReplyResponse.builder()
                .rno(reply.getRno())
                .reply(reply.getReply())
                .deph(reply.getDeph())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .pno(pno)
                .uno(reply.getUser().getUno())
                .cno(reply.getParentReply() != null ? reply.getParentReply().getRno() : null)
                .uname(reply.getUser().getUname())
                .build();
    }
    public ReplyRequest toReplyResponse(Reply reply){
        return ReplyRequest.builder()
                .rno(reply.getRno())
                .reply(reply.getReply())
                .deph(reply.getDeph())
                .build();
    }
}
