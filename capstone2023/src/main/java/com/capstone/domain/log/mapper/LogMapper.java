package com.capstone.domain.log.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.capstone.domain.log.dto.LogRequest;
import com.capstone.domain.log.dto.LogResponse;
import com.capstone.domain.log.entity.Log;
import com.capstone.domain.logState.entity.LogState;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.user.entity.User;

import jakarta.validation.Valid;

@Component
public class LogMapper {
		
	public Log toEntity(Post post, LogState logState, User user, @Valid LogRequest logRequest  ) {
		return Log.builder()
				.lno(logRequest.getLno())
				.user(user)
				.post(post)
				.logState(logState)
				.regDate(logRequest.getRegDate())
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
}
