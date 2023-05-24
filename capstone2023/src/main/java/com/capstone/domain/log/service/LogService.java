package com.capstone.domain.log.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.domain.log.dto.LogRequest;
import com.capstone.domain.log.dto.LogResponse;
import com.capstone.domain.log.entity.Log;
import com.capstone.domain.log.mapper.LogMapper;
import com.capstone.domain.log.repository.LogRepository;
import com.capstone.domain.logState.entity.LogState;
import com.capstone.domain.logState.repository.LogStateRepository;
import com.capstone.domain.logState.exception.*;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.post.repository.PostRepository;
import com.capstone.domain.post.exception.*;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.repository.UserRepository;
import com.capstone.domain.user.exception.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogService {
	private final LogMapper logMapper;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final LogStateRepository logStateRepository;
	private final LogRepository logRepository;
		
	//일단 로그 생성하는거 
	//로그생성하는 경우는 게시글 등록을 했을때, 파일을 다운로드 받았을떄, 
	@Transactional
	public Log LogCreate(LogRequest logRequest) {
		System.out.println("로그 생성 요청");
		Post post = this.postRepository.findByPno(logRequest.getPno()).orElseThrow(()-> new PostNotFoundException());
		LogState logState = this.logStateRepository.findByLsno(logRequest.getLsno()).orElseThrow(() -> new LogStateNotFoundException());
		User user = this.userRepository.findByUno(logRequest.getUno()).orElseThrow(() -> new UserNotFoundException());
		User puser = this.userRepository.findByUno(logRequest.getPuno()).orElseThrow(() -> new UserNotFoundException());
		Log log = logMapper.toEntity(post, logState, user, logRequest);
		return this.logRepository.save(log);
	}
	
	
	//달력로그 호출하는 메소드
	@Transactional
	public Page<LogResponse> getList(int page, String date, Long uno){
		//Pageable pageable = PageRequest.of(page,10);
		Pageable pageable = PageRequest.of(page,100);
		Page <Log> logList = this.logRepository.findByUserAndRegDate(uno, date, pageable);
 		return logList.map(log -> this.logMapper.toLogResponse(log));
	}
	
	
		
	//다운로드 로그 호출하는 메소드.
	@Transactional
	public Page<LogResponse> getDownList(Long puno, int page){
		Pageable pageable = PageRequest.of(page,100);
		//Pageable pageable = PageRequest.of(page,10);
		Page <Log> logList = this.logRepository.findByPuserAndLogState(puno, 2L, pageable);
 		return logList.map(log -> this.logMapper.toLogResponse(log));
		
	}
	
	@Transactional
	public Page<LogResponse> getTagList(Long uno, int page){
		//Pageable pageable = PageRequest.of(page,10);
		Pageable pageable = PageRequest.of(page,100);
		Page <Log> logList = this.logRepository.findByUserAndLogState(uno, 2L, pageable);
 		return logList.map(log -> this.logMapper.toLogResponse(log));
		
	}
	

}
