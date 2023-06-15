package com.capstone.domain.posthashtag.entity;

import com.capstone.domain.hashtag.entity.Hashtag;
import com.capstone.domain.post.entity.Post;
import com.capstone.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "post_hashtag")
public class PostHashtag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phno")
    private Long phno; // 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "htno")
    private Hashtag hashtag;  //해시태그 키(해시태그) 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hno")
    private Post post;  //해시태그 아이디(게시글) 참조
}
