package com.capstone.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.domain.board.entity.Board;
import com.capstone.domain.board.repository.BoardRepository;

@SpringBootTest
public class BoardRepositoryTest {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Test
	public void insertBoard() {
		Board board = Board.builder()
				.bname("베스트")
				.build();
		boardRepository.save(board);
		System.out.println(board);
	}

}
