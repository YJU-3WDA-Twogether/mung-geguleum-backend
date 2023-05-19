package com.capstone.domain.post.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.capstone.domain.board.entity.Board;
import com.capstone.domain.file.dto.FileDTO;
import com.capstone.domain.file.mapper.FileMapper;
import com.capstone.domain.post.dto.PostRequest;
import com.capstone.domain.post.dto.PostResponse;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.repository.UserGradeRepository;
import com.capstone.domain.user.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostMapper {
	
	private final FileMapper fileMapper;
	public Post toEntity(@Valid PostRequest postDTO ) {
		return Post.builder()
				.pno(postDTO.getPno())
				.title(postDTO.getTitle())
				.content(postDTO.getContent())
				.build();
	}
	
	public Post toEntity(@Valid PostRequest postDTO , Board board, User user) {
		return Post.builder()
				.pno(postDTO.getPno())
				.title(postDTO.getTitle())
				.content(postDTO.getContent())
				.user(user)
				.board(board)
				.build();
	}
	
	
	


	
	
	//DTO에 Entity 담기 
	public PostRequest toPostPostRequest(Post post) {
		return PostRequest.builder()
				.pno(post.getPno())
				.title(post.getTitle())
				.content(post.getContent())
				.regDate(post.getRegDate())
				.modDate(post.getModDate())
				.build();
	}
	
	//DTO에 Entity 담기 
		public PostResponse toPostResponse(Post post) {
			return PostResponse.builder()
					.pno(post.getPno())
					.title(post.getTitle())
					.content(post.getContent())
					.regDate(post.getRegDate())
					.modDate(post.getModDate())
					.build();
		}
	
	//DTO에 Entity 담기 페이징 기능을 할때 파일도 담아서 같이보낸다. postEntity에서 조인을 통해서 데이터 호출해야함.
		public PostResponse toPostResponse(Post post, List<FileDTO> file) {
			return PostResponse.builder()
					.pno(post.getPno())
					.title(post.getTitle())
					.content(post.getContent())
					.regDate(post.getRegDate())
					.modDate(post.getModDate())
					.uid(post.getUser().getUid())
					.uno(post.getUser().getUno())
					.bname(post.getBoard().getBname())
					.file(file)
					.build();
		}
		
		
		
		//DTO에 Entity 담기 
				public PostResponse toPostResponse(Post post,Board board, User user) {
					return PostResponse.builder()
							.pno(post.getPno())
							.title(post.getTitle())
							.content(post.getContent())
							.regDate(post.getRegDate())
							.modDate(post.getModDate())
							.bname(board.getBname())
							.uid(user.getUid())
							.uno(post.getUser().getUno())
							.file(post.getFiles().stream().map(file -> fileMapper.toFileDTO(file,post.getPno())).collect(Collectors.toList()))
							//.file((post.getFiles()).map(file -> this.fileMapper.toFileDTO(file)))
							.build();
					
							
				}
}
