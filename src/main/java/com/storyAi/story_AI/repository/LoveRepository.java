package com.storyAi.story_AI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.storyAi.story_AI.dto.LoveResponse;
import com.storyAi.story_AI.entity.Love;

@Repository
public interface LoveRepository extends JpaRepository<Love, Long> {
Love findByUserIdAndBook_BookId(Long userId,Long bookId); 
@Query("SELECT new com.storyAi.story_AI.dto.LoveResponse(l.lovedAt, b.nameBook, b.outhor, b.language, b.about, b.introduction, b.pages, b.coverImage,b.createdBook) " +
	       "FROM Love l JOIN l.book b WHERE l.user.id = :userId ORDER BY l.lovedAt DESC")
	List<LoveResponse> getLoves(@Param("userId") Long userId);
}
