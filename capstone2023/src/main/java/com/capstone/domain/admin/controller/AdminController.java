package com.capstone.domain.admin.controller;

import com.capstone.global.security.jwt.JwtAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capstone.domain.admin.service.AdminService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
   
   private final AdminService adminService;

   @PutMapping("/blackPostDelete/{pno}")
   public ResponseEntity<Boolean> blackPostDelete(@PathVariable Long pno , @AuthenticationPrincipal JwtAuthentication user){
	   System.out.println("여오나?");
      this.adminService.blackPostDelete(pno, user.uno,user.role);
      return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
   }
}