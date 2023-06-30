package com.capstone.domain.reply.repository;

import com.capstone.domain.post.entity.Post;
import com.capstone.domain.reply.entity.Reply;
import com.capstone.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Optional<Object> findByRno(Long cno);

    List<Reply> findByPostPno(Long pno);

    @Query("SELECT r FROM Reply r WHERE r.user.uno = :uno")
    Page<Reply> findByUserUno(Pageable pageable, Long uno);
}
