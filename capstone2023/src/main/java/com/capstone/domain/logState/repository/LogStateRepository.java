package com.capstone.domain.logState.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstone.domain.log.entity.Log;
import com.capstone.domain.logState.entity.LogState;

public interface LogStateRepository extends JpaRepository<LogState, Long> {
	Optional<LogState>findByLsno(Long lsno);
}
