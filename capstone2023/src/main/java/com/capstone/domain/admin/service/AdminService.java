package com.capstone.domain.admin.service;

import ch.qos.logback.classic.spi.LogbackServiceProvider;
import com.capstone.domain.log.service.LogService;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.post.exception.PostForbiddenException;
import com.capstone.domain.post.exception.PostNotFoundException;
import com.capstone.domain.post.service.PostService;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.exception.UserInvalidException;
import com.capstone.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminService {
        private final PostService postService;
        private final LogService logService;

        @Transactional
        public void blackPostDelete(Long pno, Long uno, String role) {
            if(role.equals("ADMIN")) {
                postService.postDelete(pno, uno, role); //게시글 삭제
                logService.blackComplete(pno, uno, role);   //게시글 신고완료처리
            }
        }
}