package com.capstone.domain.hashtag.repository;

import com.capstone.domain.hashtag.entity.Hashtag;
import com.capstone.domain.posthashtag.entity.PostHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Hashtag findByTitle(String hashtag);
}
