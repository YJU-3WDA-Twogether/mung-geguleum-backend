package com.capstone.domain.hashtag.entity;

import com.capstone.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "hashtag")
public class Hashtag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "htno")
    private Long htno; // 아이디

    @Column(length=255)
    private String title; // 제목
}
