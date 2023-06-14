package com.capstone.domain.user.controller;

import com.capstone.global.security.jwt.JwtAuthentication;
import com.capstone.global.security.jwt.dto.TokenInfo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstone.domain.user.dto.UserCreateForm;
import com.capstone.domain.user.dto.UserDTO;
import com.capstone.domain.user.dto.UserLoginForm;
import com.capstone.domain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")

public class UserController {

	private final UserService userService;

	//사용자 전체조회 json형태로 반환
	@ResponseBody
	@GetMapping("/list")
	public ResponseEntity<Page> list (@RequestParam(value="page", defaultValue="0") int page) {
		Page<UserDTO> list = this.userService.getList(page);
		return ResponseEntity.ok(list);

	}

	//json형태로 데이터를 보내야함.
	//사용자 생성 json형태로 반환.
	@PostMapping("/create")
	public ResponseEntity<Boolean> userCreate(@Valid @RequestBody UserCreateForm userCreateForm) {
		userService.userCreate(userCreateForm);
		return ResponseEntity.ok(true);
	}

	@ResponseBody
	@GetMapping("/read/{uno}")
	public ResponseEntity<UserDTO> userGet (@PathVariable Long uno) {
		//System.out.println(uno);
		UserDTO userDTO = this.userService.userGet(uno);
		return ResponseEntity.ok(userDTO);
	}

	@PutMapping("/update/{uno}")
	public ResponseEntity<UserDTO> userUpdate(@PathVariable Long uno ,@Valid @RequestBody UserDTO userDTO, @AuthenticationPrincipal JwtAuthentication user ) {
		UserDTO updateUser = userService.userUpdate(uno, userDTO, user.uno);
		return ResponseEntity.ok(updateUser);
	}


	//사용자 삭제 
	@DeleteMapping("/delete/{uno}")
	public ResponseEntity<Boolean> userDelete(@PathVariable Long uno, @AuthenticationPrincipal JwtAuthentication user ){
		this.userService.userDelete(uno,user.uno);
		return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
	}


	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@Valid @RequestBody UserLoginForm userLoginForm , HttpServletResponse response) {
		TokenInfo tokenInfo =userService.login(userLoginForm.getUid(), userLoginForm.getPassword());
		Cookie refreshTokenCookie = new Cookie("refreshToken", tokenInfo.getRefreshToken());
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(false);
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setDomain("localhost");
		refreshTokenCookie.setMaxAge(14 * 24 * 60 * 60);
		response.addCookie(refreshTokenCookie);
		return ResponseEntity.ok(tokenInfo.getAccessToken());
	}

	//유저 관련 중복 체크란
	//userid중복체크
	@GetMapping("/useridchk/{userid}")
	public ResponseEntity<Boolean>  useridchk(@PathVariable String userid) {
		boolean b = this.userService.uidchk(userid);

		if(b) { //중복이라면
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		}else {
			return ResponseEntity.ok(true);
		}
	}

	//email중복체크
	@GetMapping("/emailchk/{email}")
	public ResponseEntity<Boolean> emailchk(@PathVariable String email){
		boolean b = this.userService.emailchk(email);

		if(b){
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		}else {
			return ResponseEntity.ok(true);
		}

	}

	//nickname중복체크
	@GetMapping("/nicknamechk/{nickname}")
	public ResponseEntity<Boolean> nicknamechk(@PathVariable String nickname){
		boolean b = this.userService.nicknamechk(nickname);

		if(b){
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		}else {
			return ResponseEntity.ok(true);
		}
	}
	//유저 중복 체크 끝
	
	@GetMapping("/logout")
	public ResponseEntity<?> userLogout(HttpServletResponse response,@AuthenticationPrincipal JwtAuthentication user ) {
		
		TokenInfo tokenInfo =userService.logout(user.uno);
		Cookie refreshTokenCookie = new Cookie("refreshToken", tokenInfo.getRefreshToken());
//		refreshTokenCookie.setHttpOnly(true);
//		refreshTokenCookie.setSecure(false);
		refreshTokenCookie.setPath("/");
//		refreshTokenCookie.setDomain("localhost");
		refreshTokenCookie.setMaxAge(0);
		response.addCookie(refreshTokenCookie);
		return ResponseEntity.ok(tokenInfo.getAccessToken());
	}
}
