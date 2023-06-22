package com.capstone.domain.userFile.controller;

import com.capstone.domain.userFile.dto.UserFileRequest;
import com.capstone.domain.userFile.service.UserFileService;
import com.capstone.global.security.jwt.JwtAuthentication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/userFile")
public class UserFileController {
    private final UserFileService userFileService;

    @PutMapping("/updateFile")
    public ResponseEntity<Boolean> updateFile(@Valid UserFileRequest userFileRequest, @AuthenticationPrincipal JwtAuthentication user) throws Exception {
        userFileService.updateProfile(userFileRequest, user.uno);
        return ResponseEntity.ok(true);
    }
}
