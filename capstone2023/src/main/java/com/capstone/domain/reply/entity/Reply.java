package com.capstone.domain.reply.entity;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.user.entity.User;
import com.capstone.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Reply extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;   //댓글번호

    @Column(length=255)
    private String comment; //댓글

    @Column
    private Long deph;  //댓글 깊이

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;  //유저번호 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no")
    private Post post;  //게시글 번호 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_reply_no")
    private Reply parentReply;  // 부모 댓글 참조(대댓글)

}
