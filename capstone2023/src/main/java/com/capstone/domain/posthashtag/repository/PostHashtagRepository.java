package com.capstone.domain.posthashtag.repository;

import com.capstone.domain.post.entity.Post;
import com.capstone.domain.posthashtag.entity.PostHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long> {
    @Modifying
    @Query("DELETE FROM PostHashtag ph WHERE ph.post.pno = :pno")
    int deleteByPno(Long pno);

    @Query("SELECT ph FROM PostHashtag ph WHERE ph.post.pno = :pno")
    List<PostHashtag> findByPno(Long pno);

    List<PostHashtag> findByPost_Pno(Long pno);

    void deleteAll(Iterable<? extends PostHashtag> entities);
}
