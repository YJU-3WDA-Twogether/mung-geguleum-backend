package com.capstone.domain.heart.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Builder
@Getter
@Setter
@ToString
@Component
@NoArgsConstructor
@AllArgsConstructor
public class HeartRequest {

    private Long uno;

    private Long pno;

}
