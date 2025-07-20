package com.storyAi.story_AI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.storyAi.story_AI.entity.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
Rate findByUserId(Long userId);

}
