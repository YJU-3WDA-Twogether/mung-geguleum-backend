package com.capstone.domain.reply.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.domain.post.entity.Post;
import com.capstone.domain.post.exception.PostNotFoundException;
import com.capstone.domain.post.repository.PostRepository;
import com.capstone.domain.reply.dto.ReplyRequest;
import com.capstone.domain.reply.entity.Reply;
import com.capstone.domain.reply.exception.ReplyForbiddenException;
import com.capstone.domain.reply.exception.ReplyNotFoundException;
import com.capstone.domain.reply.exception.ReplyNullPointException;
import com.capstone.domain.reply.mapper.ReplyMapper;
import com.capstone.domain.reply.repository.ReplyRepository;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.exception.UserNotFoundException;
import com.capstone.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final ReplyMapper replyMapper;



    @Transactional
    public void replyCreate(ReplyRequest replyDTO,Long uno) {

        // 유저 참조 설정
        User user = this.userRepository.findByUno(uno)
                .orElseThrow(() -> new UserNotFoundException());

        // 게시글 참조 설정
        Post post = this.postRepository.findByPno(replyDTO.getPno())
                .orElseThrow(() -> new PostNotFoundException());


        // 부모 댓글 참조 설정(대댓글일 경우)
        Reply parentReply = null;
        if (replyDTO.getDeph() == 2) {
            parentReply = (Reply) replyRepository.findByRno(replyDTO.getCno())
                    .orElseThrow(() -> new ReplyNotFoundException());
        }

        Reply reply = replyMapper.toEntity(replyDTO, user, post, parentReply);
        replyRepository.save(reply);
    }

    @Transactional
    public void replyDelete(Long rno,Long uno) {
        Reply reply = (Reply) replyRepository.findByRno(rno)
                .orElseThrow(() -> new ReplyNullPointException());
        if(!reply.getUser().getUno().equals(uno)) {
        	throw new ReplyForbiddenException();
        }
        // 댓글 삭제 로직
        replyRepository.delete(reply);
    }

    @Transactional
    public void replyUpdate(ReplyRequest replyDTO,Long uno) {

        // 유저 참조 설정
        User user = this.userRepository.findByUno(uno)
                .orElseThrow(() -> new UserNotFoundException());

        // 게시글 참조 설정
        Post post = this.postRepository.findByPno(replyDTO.getPno())
                .orElseThrow(() -> new PostNotFoundException());
        
        // 부모 댓글 참조 설정(대댓글일 경우)
        Reply parentReply = null;
        if (replyDTO.getDeph() == 2) {
            parentReply = (Reply) replyRepository.findByRno(replyDTO.getCno())
                    .orElseThrow(() -> new ReplyNotFoundException());
        }
        // 댓글 수정 로직
        Reply reply = replyMapper.toEntity(replyDTO, user, post, parentReply);
        if(!reply.getUser().getUno().equals(uno)) {
        	throw new ReplyForbiddenException();
        }
        replyRepository.save(reply);
    }
}
