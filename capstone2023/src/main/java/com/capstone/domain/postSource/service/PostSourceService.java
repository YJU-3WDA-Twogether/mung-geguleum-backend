package com.capstone.domain.postSource.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.domain.log.entity.Log;
import com.capstone.domain.log.exception.LogNotFoundException;
import com.capstone.domain.log.repository.LogRepository;
import com.capstone.domain.post.dto.PostResponse;
import com.capstone.domain.post.entity.Post;
import com.capstone.domain.post.mapper.PostMapper;
import com.capstone.domain.postSource.dto.PostSourceResponse;
import com.capstone.domain.postSource.entity.PostSource;
import com.capstone.domain.postSource.mapper.PostSourceMapper;
import com.capstone.domain.postSource.repository.PostSourceRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PostSourceService {
	
	private final PostSourceRepository postSourceRepository;
	private final LogRepository logRepository;
	private final PostSourceMapper postSourceMapper;
	private final PostMapper postMapper;
	
	
	
	//태그 생성 메소드 만들기
	//태그 생성메소드는 postService 에서 만들어서 값을 받아서 할당해야함.
	//post Service에서 받을값은 게시글 pno와 로그 lno 의 값을 담아서 넣어줘야함.
	//근데 여기서 lno의 값은 post 할 경우 담겨져있는 tag [] 에서 값을추출해서 넣어줘야함. 
	@Transactional
	public void postSourceCreate(Long[] postSources , Post post) {
		
		//지금은 임시로 Object이지만 추후에 front에서 데이터를 전송하는 타입으로 변경해서 다시받아야함.
		for(Long postSource : postSources) {
			//tag 안에 담긴 lno를 바탕으로 찾아서 집어넣고 지금은 테스트로 1L을 넣음.
			Log log = this.logRepository.findById(postSource).orElseThrow(() -> new LogNotFoundException());
			this.postSourceRepository.save(this.postSourceMapper.toEntity(post, log.getPost()));
			//Tag tag = this.tagMapper.toEntity( post, log);
			//this.postSourceRepository.save(tag);
			
		}
	}
	@Transactional
	public List<Object> getPostSource() {
	    List<PostSource> list = postSourceRepository.findAll();
	    Set<PostSourceResponse> nodeList = new HashSet<>();
	    Set<Map<String, Long>> linkList = new HashSet<>();

	    for (PostSource result : list) {
	    	//node를 추가하는 부분.
	        nodeList.add(postSourceMapper.toPostSourceResponse(result.getPost()));
	        nodeList.add(postSourceMapper.toPostSourceResponse(result.getParentPost()));
	    	    
	        // Link 맵을 추가하는 부분
	        Map<String, Long> linkMap = new HashMap<>();
	        linkMap.put("source", result.getParentPost().getPno()); // 부모 노드의 pno
	        linkMap.put("target", result.getPost().getPno()); // 자식 노드의 pno
	        linkList.add(linkMap);
	    }

	    // 결과값을 Map에 담아서 반환
	    Map<String, Object> resultMap = new HashMap<>();
	    resultMap.put("nodes", nodeList);
	    resultMap.put("links", linkList);

	    return Collections.singletonList(resultMap);
	}
	
	@Transactional
	public List<Object> getPostSource(Long pno) {
		  List<PostSource> list = postSourceRepository.findAllByPno(pno);
		    Set<PostSourceResponse> nodeList = new HashSet<>();
		    Set<Map<String, Long>> linkList = new HashSet<>();

		    for (PostSource result : list) {
		    	//node를 추가하는 부분.
		        nodeList.add(postSourceMapper.toPostSourceResponse(result.getPost()));
		        nodeList.add(postSourceMapper.toPostSourceResponse(result.getParentPost()));
		    	    
		        // Link 맵을 추가하는 부분
		        Map<String, Long> linkMap = new HashMap<>();
		        linkMap.put("source", result.getParentPost().getPno()); // 부모 노드의 pno
		        linkMap.put("target", result.getPost().getPno()); // 자식 노드의 pno
		        linkList.add(linkMap);
		    }

		    // 결과값을 Map에 담아서 반환
		    Map<String, Object> resultMap = new HashMap<>();
		    resultMap.put("nodes", nodeList);
		    resultMap.put("links", linkList);

		    return Collections.singletonList(resultMap);
	}

	
}
