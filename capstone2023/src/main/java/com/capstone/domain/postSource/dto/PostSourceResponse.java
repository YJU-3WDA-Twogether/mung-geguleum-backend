package com.capstone.domain.postSource.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostSourceResponse {

	private Long pno;
	private Long uno;
	private String nickname;
	private String title;
	private String fpath;
	private LocalDateTime regDate;

	 @Override

	  public int hashCode() {

	    return Objects.hash(pno);

	  }




	  @Override
	  public boolean equals(Object obj) {
	    if (obj instanceof PostSourceResponse) {
	    	PostSourceResponse tmp = (PostSourceResponse) obj;
	      return pno.equals(tmp.pno);

	    }
	    return false;
	  }
	
}
