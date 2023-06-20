package com.capstone.domain.post.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.capstone.domain.board.entity.Board;
import com.capstone.domain.file.dto.FileDTO;
import com.capstone.domain.file.mapper.FileMapper;
import com.capstone.domain.heart.Mapper.HeartMapper;
import com.capstone.domain.heart.dto.HeartDTO;
import com.capstone.domain.post.dto.PostRequest;
import com.capstone.domain.post.dto.PostResponse;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.reply.dto.ReplyResponse;
import com.capstone.domain.reply.mapper.ReplyMapper;
import com.capstone.domain.user.entity.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostMapper {
	
	private final FileMapper fileMapper;
	private final HeartMapper heartMapper;
	private final ReplyMapper replyMapper;
	
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
		
	//DTO에 Entity 담기 페이징 기능을 할때 파일도 담아서 같이보낸다. postEntity에서 조인을 통해서 데이터 호출해야함.
		public PostResponse toPostResponse(Post post) {

			return PostResponse.builder()
					.pno(post.getPno())
					.title(post.getTitle())
					.content(post.getContent())
					.regDate(post.getRegDate())
					.modDate(post.getModDate())
					.uid(post.getUser().getUid())
					.uno(post.getUser().getUno())
					.bname(post.getBoard().getBname())
					.file(post.getFiles().stream().map(file -> fileMapper.toFileDTO(file,post.getPno())).collect(Collectors.toList()))
					.reply(post.getReplys().stream().map(reply -> replyMapper.toReplyResponse(reply,post.getPno())).collect(Collectors.toList()))
					.build();
		}
		
		
		
		//DTO에 Entity 담기 
				public PostResponse toPostResponse(Post post,Board board, User user,  Long uno) {
					Long logCount = post.getLogs().stream()
							.filter(log -> log.getLogState().getLsno() == 2)
							.count();
					boolean hExist = post.getHearts().stream()
							.anyMatch(heart -> heart.getUser().getUno().equals(uno));
					return PostResponse.builder()
							.pno(post.getPno())
							.title(post.getTitle())
							.content(post.getContent())
							.regDate(post.getRegDate())
							.modDate(post.getModDate())
							.bname(board.getBname())
							.uid(user.getUid())
							.uno(post.getUser().getUno())
							.rCount(Long.valueOf(post.getReplys().size()))
							.hCount(Long.valueOf(post.getHearts().size()))
							.lCount(logCount)
							.hExist(hExist)
							.file(post.getFiles().stream().map(file -> fileMapper.toFileDTO(file,post.getPno())).collect(Collectors.toList()))
							.build();
					
							
				}
				
				//getMyPost를 할때 사용합니다.
				public PostResponse toPostResponse(Post post,Board board, User user) {
					Long logCount = post.getLogs().stream()
							.filter(log -> log.getLogState().getLsno() == 2)
							.count();
					boolean hExist = post.getHearts().stream()
							.anyMatch(heart -> heart.getUser().getUno().equals(user.getUno()));
					
					return PostResponse.builder()
							.pno(post.getPno())
							.title(post.getTitle())
							.content(post.getContent())
							.regDate(post.getRegDate())
							.modDate(post.getModDate())
							.bname(board.getBname())
							.uid(user.getUid())
							.uno(post.getUser().getUno())
							.rCount(Long.valueOf(post.getReplys().size()))
							.hCount(Long.valueOf(post.getHearts().size()))
							.lCount(logCount)
							.hExist(hExist)
							.file(post.getFiles().stream().map(file -> fileMapper.toFileDTO(file,post.getPno())).collect(Collectors.toList()))
							.build();
					
							
				}
}
