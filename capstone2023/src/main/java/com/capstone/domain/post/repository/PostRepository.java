package com.capstone.domain.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capstone.domain.board.entity.Board;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.user.entity.User;


public interface PostRepository extends JpaRepository<Post, Long>{

	Post findByTitle(String title);
	//findBySubject를 사용하고싶다면 사용하고싶은 인터페이스에 findBySubject를 추가해줘야한다.
	
	Post findByTitleAndContent(String title, String content);
	//컬럼명중 subject와 content의 밸류를 찾는 메서드이다.
	
	Optional<Post> findByPno(Long pno);
	
	List<Post> findByTitleLike(String title);
	
	Page<Post> findAll(Pageable pageable);
	
	Page<Post> findAllByUserAndBoard(User user, Board board, Pageable pageable);
	
	
	    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.files")
	    List<Post> findAllWithFiles();
	    
//	    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.files where p.pno = :pno ")
//	    List<Object[]> findBypnoWithFiles();
	    
	    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.files JOIN p.board b Join p.user u LEFT Join p.replys WHERE p.pno = :pno")
	    Optional<Post> findByFilesAndReply(@Param("pno") Long pno);

	    
	    
	  //모든게시판을 조회하기위해서 사용하는 쿼리다. 애는 된다.
		  @Query("SELECT distinct p, b, u FROM Post p JOIN p.board b JOIN p.user u left JOIN p.files")
		    Page<Object[]> findAllWithBoardAndUser(Pageable pageable);
		    
		//특정 게시판을 조회하는 메소드
	    @Query("SELECT distinct p, b, u FROM Post p left JOIN p.files JOIN p.board b JOIN p.user u  WHERE b.bname = :bname ORDER BY p.pno DESC")
	    Page<Object[]> findAllByBoardName(String bname, Pageable pageable);
	    
	    
	   
	   


	
}
