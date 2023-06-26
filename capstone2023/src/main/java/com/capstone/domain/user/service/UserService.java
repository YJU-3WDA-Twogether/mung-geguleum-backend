package com.capstone.domain.user.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.domain.file.service.FileService;
import com.capstone.domain.user.dto.UserCreateForm;
import com.capstone.domain.user.dto.UserDTO;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.entity.UserGrade;
import com.capstone.domain.user.exception.UserInvalidException;
import com.capstone.domain.user.exception.UserNotFoundException;
import com.capstone.domain.user.mapper.UserMapper;
import com.capstone.domain.user.repository.UserGradeRepository;
import com.capstone.domain.user.repository.UserRepository;
import com.capstone.global.security.jwt.dto.TokenInfo;
import com.capstone.global.security.jwt.service.TokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserGradeRepository userGradeRepository;
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final TokenService tokenService;
	private final FileService fileService;
	//	private final PasswordEncoder passwordEncoder;
	
//
	//유저 회원가입 메소드
	public void userCreate(UserCreateForm userCreateForm) {
//			user.setPassword(passwordEncoder.encode(password));
		UserGrade userGrade = userGradeRepository.findByGname("USER").get();
		User user= userMapper.toEntity(userCreateForm,userGrade);
		
		user = this.userRepository.save(user);
		fileService.basicImg(user);
	}

	//유저 본인정보 조회메소드
	@Transactional
	public UserDTO userGet(Long uno) {
		User user = this.userRepository.findByUno(uno).orElseThrow(()-> new UserNotFoundException());
		return userMapper.toUserDTO(user);
	}

	//유저 정보 수정 메소드
	@Transactional
	public UserDTO userUpdate(Long uno , UserDTO userDTO, Long uno2,String role) {
		if(role.equals("ADMIN")) {
			Optional<User> user = userRepository.findById(uno2);
			if(!user.get().getUserGrade().getGname().equals("ADMIN")) {
				throw new UserInvalidException();
			}
		}else if(!uno.equals(uno2)) {
			System.out.println("uno : "+uno + "uno2 : "+uno2);
			throw new UserInvalidException();	
		}
		User user = userRepository.findByUno(uno).orElseThrow(()-> new UserNotFoundException());
		Optional<UserGrade> grade = userGradeRepository.findByGname(userDTO.getGrade());
		
		if(grade == null)
			user.register(userDTO.getNickname(), userDTO.getIntroduce(),user.getUserGrade());
		else 
			user.register(userDTO.getNickname(), userDTO.getIntroduce(),grade.get());
		user = userRepository.save(user);
		System.out.println(userDTO.getFile());
		if(userDTO.getFile()!= null)
			fileService.userUpdate(userDTO.getFile(),user);
		else
			fileService.basicImg(user);
		
		
		return userMapper.toUserDTO(user);
	}

	//회원삭제 메소드
	@Transactional
	public void userDelete(Long uno,Long uno2, String role) {
		System.out.println(role);
		if(role.equals("ADMIN")) {
			Optional<User> user = userRepository.findById(uno2);
			if(!user.get().getUserGrade().getGname().equals("ADMIN")) {
				throw new UserInvalidException();
			}
		}
		else if(!uno.equals(uno2)) {
			System.out.println("uno : "+uno + "uno2 : "+uno2);
			throw new UserInvalidException();	
		}
		this.userRepository.deleteById(uno);
	}
	//유저 로그인 메소드
	@Transactional
	public TokenInfo login(String uid, String password) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(uid, password);

		// 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
		// authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// 3. 인증 정보를 기반으로 JWT 토큰 생성
		TokenInfo tokenInfo = tokenService.generateToken(authentication);

		return tokenInfo;
		//	return null;

	}

	//페이징 사용한 유저 조회
	@Transactional
	public Page<UserDTO> getList(int page) {
		Pageable pageable = PageRequest.of(page,30);
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
	
	@Transactional
	public TokenInfo logout(Long uno) {
		User user = userRepository.findById(uno).orElseThrow(() -> new UserNotFoundException());
		TokenInfo tokenInfo = tokenService.deleteToken(user.getUno());
		return tokenInfo;
		//	return null;

	}
}
