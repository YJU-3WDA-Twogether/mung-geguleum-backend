package com.capstone.domain.postSource.entity;

import com.capstone.domain.log.entity.Log;
import com.capstone.domain.post.entity.Post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class PostSource {
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long psno;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_pno")
    private Post parentPost;
}
