package com.capstone.domain.heart.controller;


import com.capstone.domain.heart.dto.HeartDTO;
import com.capstone.domain.heart.service.HeartService;
import com.capstone.global.security.jwt.JwtAuthentication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/heart")
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> insert(@RequestBody HeartDTO heartDTO, @AuthenticationPrincipal JwtAuthentication user) {
        heartService.create(heartDTO, user.uno);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestBody HeartDTO heartDTO, @AuthenticationPrincipal JwtAuthentication user) {
        heartService.delete(heartDTO, user.uno);
        return ResponseEntity.ok(true);
    }
}
