package com.storyAi.story_AI.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.storyAi.story_AI.dto.HistoryResponse;
import com.storyAi.story_AI.entity.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
	@Query("SELECT new com.storyAi.story_AI.dto.HistoryResponse(h.createdAt, b.nameBook, b.outhor, b.language, b.about, b.introduction, b.pages, b.coverImage,b.createdBook) " +
		       "FROM History h JOIN h.book b WHERE h.user.id = :userId ORDER BY h.createdAt DESC")
		List<HistoryResponse> getHistories(@Param("userId") Long userId);

}
