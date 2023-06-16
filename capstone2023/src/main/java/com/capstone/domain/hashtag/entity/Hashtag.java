package com.capstone.domain.hashtag.entity;

import com.capstone.domain.posthashtag.entity.PostHashtag;
import com.capstone.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "hashtag")
public class Hashtag{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "htno")
    private Long htno; // 아이디

    @Column(length=255)
    private String title; // 제목

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL)
    private List<PostHashtag> postHashtags = new ArrayList<>();
}
