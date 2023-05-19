package com.capstone.repository;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.domain.file.entity.File;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.post.repository.PostRepository;

@SpringBootTest
public class PostRepositoryTest {

	@Autowired
	private PostRepository postRepository;
	
	@Test
	public void selectpost() {
		
		List<Post> list = this.postRepository.findAllWithFiles();
		for(Post post : list) {
			System.out.println(post.getPno());
			System.out.println(post.getTitle());
			System.out.println(post.getContent());
//			System.out.println(post.getUser().getUname());
//			System.out.println(post.getBoard().getBname());
			
			List<File> files = post.getFiles();
			for(File file : files) {
				System.out.println("파일번호입니다."+file.getFno());
			}

			
			
		}
	}
}
