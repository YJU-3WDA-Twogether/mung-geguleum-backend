package com.capstone.domain.heart.service;


import com.capstone.domain.board.entity.Board;
import com.capstone.domain.post.dto.PostResponse;
import com.capstone.domain.post.mapper.PostMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capstone.domain.board.exception.BoardNotFoundException;
import com.capstone.domain.heart.Mapper.HeartMapper;
import com.capstone.domain.heart.dto.HeartDTO;
import com.capstone.domain.heart.entity.Heart;
import com.capstone.domain.heart.exception.HeartExistException;
import com.capstone.domain.heart.exception.HeartNotFoundException;
import com.capstone.domain.heart.repository.HeartRepository;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.post.exception.PostNotFoundException;
import com.capstone.domain.post.repository.PostRepository;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.exception.UserNotFoundException;
import com.capstone.domain.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final HeartRepository heartRepository;

    private final HeartMapper heartMapper;
    private final PostMapper postMapper;
    @Transactional
    public void create(HeartDTO heartDTO, Long uno){

        User user = userRepository.findById(uno)
                .orElseThrow(() -> new UserNotFoundException());

        Post post = postRepository.findById(heartDTO.getPno())
                .orElseThrow(() -> new BoardNotFoundException());


        // 이미 좋아요되어있으면 에러 반환
        if (heartRepository.findByUserAndPost(user, post).isPresent()){
            throw new HeartExistException();
        }

        Heart heart = heartMapper.toEntity(post, user);

        heartRepository.save(heart);
    }

    @Transactional
    public void delete(HeartDTO heartDTO, Long uno) {

        User user = userRepository.findById(uno)
                .orElseThrow(() -> new UserNotFoundException());

        Post post = postRepository.findById(heartDTO.getPno())
                .orElseThrow(() -> new PostNotFoundException ());

        Heart heart = heartRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new HeartNotFoundException());

        heartRepository.delete(heart);
    }

    @Transactional
    public Page<PostResponse> getMyHeart(int page, Long uno) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("hno").descending());
        Page<Object[]> result = heartRepository.findByUserUno(pageable, uno);
        return result.map(objects -> {
            Post post = (Post) objects[0];
            Board board = (Board) objects[1];
            User user = (User) objects[2];
            return postMapper.toPostResponse(post,board,user, uno) ;
        });
    }
}
