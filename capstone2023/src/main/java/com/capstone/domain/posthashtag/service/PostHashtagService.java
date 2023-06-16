package com.capstone.domain.posthashtag.service;

import com.capstone.domain.hashtag.entity.Hashtag;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.posthashtag.entity.PostHashtag;
import com.capstone.domain.posthashtag.mapper.PostHashtagMapper;
import com.capstone.domain.posthashtag.repository.PostHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostHashtagService {
    private final PostHashtagRepository postHashtagRepository;

}
