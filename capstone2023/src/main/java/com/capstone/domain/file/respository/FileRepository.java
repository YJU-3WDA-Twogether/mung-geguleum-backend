package com.capstone.domain.file.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.domain.file.entity.File;


public interface FileRepository extends JpaRepository<File, Long> {
    
    Optional<File> findByFno(Long fno);
    
    List<File> findAll();
    
    File save(File file);
}
