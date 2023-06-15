package com.capstone.domain.posthashtag.repository;

import com.capstone.domain.posthashtag.entity.PostHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long> {
}
