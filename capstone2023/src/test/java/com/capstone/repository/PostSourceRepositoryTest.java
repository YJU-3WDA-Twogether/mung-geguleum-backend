package com.capstone.repository;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.domain.postSource.entity.PostSource;
import com.capstone.domain.postSource.repository.PostSourceRepository;

@SpringBootTest

public class PostSourceRepositoryTest {
	
	@Autowired
	private PostSourceRepository repository;
	
//	@Test
//	public void insertBoard() {
//		
//	}

	  @Test
	    public void getList() {
	        List<PostSource> list = repository.findAll();
	        list.forEach(i -> {
	            System.out.println(i.getPost().getPno() + " " + i.getParentPost().getPno());
	        });
	    }
	
	
}
