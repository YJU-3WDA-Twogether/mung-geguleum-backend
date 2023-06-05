package com.capstone.domain.user.service;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.domain.user.dto.UserCreateForm;
import com.capstone.domain.user.dto.UserDTO;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.entity.UserGrade;
import com.capstone.domain.user.exception.UserMissingValueException;
import com.capstone.domain.user.exception.UserNotFoundException;
import com.capstone.domain.user.mapper.UserMapper;
import com.capstone.domain.user.repository.UserGradeRepository;
import com.capstone.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserGradeRepository userGradeRepository;
	private final UserRepository userRepository;
	private final UserMapper userMapper;
//	private final PasswordEncoder passwordEncoder;
//	
	//유저 회원가입 메소드 
		public void userCreate(UserCreateForm userCreateForm) {
//			user.setPassword(passwordEncoder.encode(password));
			UserGrade userGrade = userGradeRepository.findByGname("일반").get();
			User User= userMapper.toEntity(userCreateForm,userGrade);
			this.userRepository.save(User);	
		}
	
	//유저 본인정보 조회메소드
	@Transactional
	public UserDTO userGet(Long uno) {
		User user = this.userRepository.findByUno(uno).orElseThrow(()-> new UserNotFoundException()); 
		return userMapper.toUserDTO(user);
	}
	
	//유저 정보 수정 메소드 
	@Transactional
	public UserDTO userUpdate(Long uno , UserDTO userDTO) {
		User user = this.userRepository.findByUno(uno).orElseThrow(()-> new UserNotFoundException()); 
		//추후 NULLPOINTEXCETPION 처리도 추가해야함. 
		user = userRepository.save(userMapper.toEntity(userDTO));
		return userMapper.toUserDTO(user);
	}
	
	//회원삭제 메소드
	@Transactional
	public void userDelete(Long uno) {
		this.userRepository.deleteById(uno);	
	}
	//유저 로그인 메소드 
	//추후에 시큐리티 및 jwt 발급을 추가해야함 23.04.04 작성한메소드. 
	@Transactional
	public UserDTO login(String uid, String password) {
		User user = this.userRepository.findByUidAndPassword(uid, password).orElseThrow(()-> new UserMissingValueException());
		return userMapper.toUserDTO(user);
	}
		
	//페이징 사용한 유저 조회
	@Transactional
	 public Page<UserDTO> getList(int page) {
		Pageable pageable = PageRequest.of(page,10);
		Page <User> userList =  this.userRepository.findAll(pageable);
		return userList.map(user -> userMapper.toUserDTO(user));
	 }
	 
	//uid 중복체크메소드
	@Transactional
		public boolean uidchk(String uid) {
			Optional<User> user = this.userRepository.findByUid(uid);
			
			if(user.isPresent()) {
				return true;
			}else {
				return false;
			}
		}
		
	//email중복체크메소드
	@Transactional
		public boolean emailchk(String email) {
			Optional<User> user = this.userRepository.findByEmail(email);
			if(user.isPresent()) {
				return true;
			}else {
				return false;
			}
		}
		
	//nickname중복체크메소드
	@Transactional
		public boolean nicknamechk(String nickname) {
			Optional<User> user = this.userRepository.findByNickname(nickname);
			if(user.isPresent()) {
				return true;
			}else {
				return false;
			}
		}

}
