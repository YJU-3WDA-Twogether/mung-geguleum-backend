package com.capstone.domain.log.mapper;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.capstone.domain.file.mapper.FileMapper;
import com.capstone.domain.log.dto.LogRequest;
import com.capstone.domain.log.dto.LogResponse;
import com.capstone.domain.log.entity.Log;
import com.capstone.domain.logState.entity.LogState;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.user.entity.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LogMapper {
	
	private final FileMapper fileMapper;
		
	public Log toEntity(Post post, LogState logState, User user, @Valid LogRequest logRequest  ) {
		return Log.builder()
				.lno(logRequest.getLno())
				.user(user)
				.post(post)
				.logState(logState)
				.regDate(logRequest.getRegDate())
				.build();
	}
	
	public Log toEntity(LogState logState, Post post, User user ) {
		LocalDateTime time = LocalDateTime.now();
		return Log.builder()
				.user(user)
				.post(post)
				.logState(logState)
				.regDate(time)
				.build();
	}
	
	
	
	public LogRequest toRequestLog(Long lsno, Long uno, Long pno, LocalDateTime time) {
		return LogRequest.builder()
				.lsno(lsno)
				.uno(uno)
				.puno(uno)
				.pno(pno)
				.regDate(time)
				.build();
	}
	
	public LogRequest toRequestLog(Long lsno, Long uno,Long puno, Long pno, LocalDateTime time) {
		return LogRequest.builder()
				.lsno(lsno)
				.uno(uno)
				.puno(puno)
				.pno(pno)
				.regDate(time)
				.build();
	}
	
	public LogResponse toLogResponse(Log log) {
		return LogResponse.builder()
				.lno(log.getLno())
				.lsname(log.getLogState().getLsname())
				.punickname(log.getPost().getUser().getNickname())
				.uno(log.getUser().getUno())
				.unickname(log.getUser().getNickname())
				.pno(log.getPost().getPno())
				.ptitle(log.getPost().getTitle())
				.regDate(log.getRegDate())
				.build();
	}
	
	public LogResponse toLogResponse2(Log log) {
		return LogResponse.builder()
				.lno(log.getLno())
				.lsname(log.getLogState().getLsname())
				.puno(log.getPost().getUser().getUno())
				.punickname(log.getPost().getUser().getNickname())
				.uno(log.getUser().getUno())
				.unickname(log.getUser().getNickname())
				.pno(log.getPost().getPno())
				.ptitle(log.getPost().getTitle())
				.file(log.getPost().getFiles().stream().map(file -> fileMapper.toFileDTO(file,log.getPost().getPno())).collect(Collectors.toList()))
				.regDate(log.getRegDate())
				.build();
	}
}
