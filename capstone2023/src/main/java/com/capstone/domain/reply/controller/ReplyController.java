package com.capstone.domain.reply.controller;

import com.capstone.domain.reply.dto.ReplyRequest;
import com.capstone.domain.reply.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/create")  // 댓글 작성
    public ResponseEntity<Boolean> replyCreate(@Valid @RequestBody ReplyRequest replyDTO) {
    	replyDTO.setUno(16L);
        replyService.replyCreate(replyDTO);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/delete/{rno}")
    public ResponseEntity<Boolean> replyDelete(@PathVariable Long rno) {
        replyService.replyDelete(rno);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/update") // 댓글 수정
    public ResponseEntity<Boolean> replyUpdate(@RequestBody ReplyRequest replyDTO) {
        replyService.replyUpdate(replyDTO);
        System.out.println("wefwef" + replyDTO.toString());
        return ResponseEntity.ok(true);
    }
}
