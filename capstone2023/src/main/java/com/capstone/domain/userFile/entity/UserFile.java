package com.capstone.domain.userFile.entity;

import com.capstone.domain.file.entity.File;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "userFile")
public class UserFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ufno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uno")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fno")
    private File file;

    @Column(length=255) //사진종류
    private String category;
}
