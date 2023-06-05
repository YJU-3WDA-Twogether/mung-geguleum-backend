package com.capstone.domain.post.entity;

import java.util.ArrayList;
import java.util.List;

import com.capstone.domain.board.entity.Board;
import com.capstone.domain.file.entity.File;
import com.capstone.domain.log.entity.Log;
import com.capstone.domain.tag.entity.Tag;
import com.capstone.domain.user.entity.User;
import com.capstone.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Post extends BaseEntity{
	
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;
    
    @Column(length=200)
    private String title;
    
    //columnDefinition은 컬럼의 속성을 정의할때 사용한다. columnDefinition="TEXT"은 내용처럼 글자수를 제한할 수 없는 경우에 사용한다.
    @Column(columnDefinition = "TEXT")
    private String content;
    
    
    //작성한 사용자를 참조하도록한다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;
    
    
    //게시판 종류 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no")
    private Board board;
        
    @OneToMany(mappedBy = "post")
    private List<File> files = new ArrayList<>();
    
    @OneToMany(mappedBy = "post")
    private List<Log> logs = new ArrayList<>();
    
    @OneToMany(mappedBy = "post")
    private List<Tag> tags = new ArrayList<>();
}
   
