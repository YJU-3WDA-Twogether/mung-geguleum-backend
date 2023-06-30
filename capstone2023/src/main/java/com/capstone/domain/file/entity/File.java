package com.capstone.domain.file.entity;


import com.capstone.domain.post.entity.Post;
import com.capstone.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long fno;
	    
	    @Column(name = "fname")
	    private String fname;
	      
	    @Column(name = "fpath")
	    private String fpath;
	    
	    @Column(name ="ftype")
	    private String ftype;
	        
	    @Column(name = "fcategory")
	    private String fcategory;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "uno")
	    private User user;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "pno")
	    private Post post;

}
