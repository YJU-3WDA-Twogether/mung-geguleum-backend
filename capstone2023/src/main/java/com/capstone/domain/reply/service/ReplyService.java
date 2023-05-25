package com.capstone.domain.reply.service;

import com.capstone.domain.reply.dto.ReplyDTO;
import com.capstone.domain.reply.entity.Reply;
import com.capstone.domain.reply.exception.ReplyNotFoundException;
import com.capstone.domain.reply.mapper.ReplyMapper;
import com.capstone.domain.reply.repository.ReplyRepository;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.post.exception.PostNotFoundException;
import com.capstone.domain.post.repository.PostRepository;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.exception.UserNotFoundException;
import com.capstone.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final ReplyMapper replyMapper;



    @Transactional
    public void replyCreate(ReplyDTO replyDTO) {
        LocalDateTime time = LocalDateTime.now();
        replyDTO.setRegDate(time);
        replyDTO.setModDate(time);


        // 유저 참조 설정
        User user = this.userRepository.findByUno(replyDTO.getUno())
                .orElseThrow(() -> new UserNotFoundException());

        // 게시글 참조 설정
        Post post = this.postRepository.findByPno(replyDTO.getPno())
                .orElseThrow(() -> new PostNotFoundException());


        // 부모 댓글 참조 설정(대댓글일 경우)
        Reply parentReply = null;
        if (replyDTO.getDeph() == 2) {
            parentReply = (Reply) replyRepository.findByAno(replyDTO.getCno())
                    .orElseThrow(() -> new ReplyNotFoundException());   //예외처리 제대로 해줘야 함
        }

        Reply reply = replyMapper.toEntity(replyDTO, user, post, parentReply);
        replyRepository.save(reply);
    }
}
