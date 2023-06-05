package com.capstone.domain.heart.controller;


import com.capstone.domain.heart.dto.HeartRequest;

import com.capstone.domain.heart.service.HeartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/get")
    public ResponseEntity<Boolean> insert(@RequestBody @Valid HeartRequest heartDTO) {
        heartService.insert(heartDTO);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestBody @Valid HeartRequest heartDTO) {
        heartService.delete(heartDTO);
        return ResponseEntity.ok(true);
    }
}
