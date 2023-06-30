package com.capstone.domain.heart.controller;


import com.capstone.domain.heart.dto.HeartDTO;
import com.capstone.domain.heart.service.HeartService;
import com.capstone.domain.post.dto.PostResponse;
import com.capstone.global.security.jwt.JwtAuthentication;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getMyHeart")
    public ResponseEntity<Page> getMyHeart(@RequestParam(value="page", defaultValue="0") int page, @AuthenticationPrincipal JwtAuthentication user){
        Page<PostResponse> paging = this.heartService.getMyHeart(page, user.uno);

        return ResponseEntity.ok(paging);
    }

}
