package com.capstone.domain.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE User SET user_grade_id = 3 WHERE uno = ?")
@Where(clause = "user_grade_id != 3 AND user_grade_id != 4") // 추가
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uno;
	
	@Column
	private String uname;
	
	@Column
	private String uid;
	
	@Column
	private String password;
	
	@Column
	private String email;
	
	@Column
	private String nickname;

	@Column(columnDefinition = "VARCHAR(255) DEFAULT '안녕하세요.'")
	private String introduce;

	@CreatedDate
	@Column(name="regdate")
	private LocalDateTime regDate;
		
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_grade_id")
    private UserGrade userGrade;

	public void updateIntroduce(String introduce) {
		this.introduce = introduce;
	}
}	
