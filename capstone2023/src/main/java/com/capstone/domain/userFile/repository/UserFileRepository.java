package com.capstone.domain.userFile.repository;

import com.capstone.domain.user.entity.User;
import com.capstone.domain.userFile.entity.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserFileRepository extends JpaRepository<UserFile, Long> {
    @Query("SELECT uf FROM UserFile uf WHERE uf.user.uid = :uno AND uf.category = :main")
    UserFile findByUnoAndCategory(Long uno, String main);
}
