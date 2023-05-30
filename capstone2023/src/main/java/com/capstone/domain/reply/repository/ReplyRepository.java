package com.capstone.domain.reply.repository;

import com.capstone.domain.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Optional<Object> findByRno(Long cno);


}
