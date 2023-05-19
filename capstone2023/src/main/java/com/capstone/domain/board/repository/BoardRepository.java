package com.capstone.domain.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.domain.board.entity.Board;
import com.capstone.domain.post.entity.Post;

public interface BoardRepository extends JpaRepository<Board, Long>{
	Optional<Board> findByBno(Long bno);

}
