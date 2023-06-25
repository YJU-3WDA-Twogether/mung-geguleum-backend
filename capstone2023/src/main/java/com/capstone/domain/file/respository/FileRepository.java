package com.capstone.domain.file.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.capstone.domain.file.entity.File;


public interface FileRepository extends JpaRepository<File, Long> {
    
    Optional<File> findByFno(Long fno);
    
    List<File> findAll();
    
    File save(File file);
    
    @Modifying
    @Query("DELETE FROM File f WHERE f.post.pno = :pno")
    int deleteByPno(Long pno);

    @Query("SELECT f FROM File f WHERE f.user.uno = :uno AND f.fcategory = :category")
    
    File findByUserAndCategory(Long uno, String category);
    
    @Modifying
    @Query("DELETE FROM File f WHERE f.user.uno = :uno")
	void deleteByUno(Long uno);
}
