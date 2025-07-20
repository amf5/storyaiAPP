package com.storyAi.story_AI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.storyAi.story_AI.entity.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long>{
	
	public List<Video> findByUserId(Long userId);
	public List<Video>findByPublished(Boolean published);
	@Query("SELECT v FROM Video v WHERE v.published = true AND v.about LIKE %:about%")
	List<Video> findByAboutLike(@Param("about") String about);

	@Query("SELECT v FROM Video v WHERE v.published = true AND v.creator LIKE %:creator%")
	List<Video> findByCreatorLike(@Param("creator") String creator);

	@Query("SELECT v FROM Video v WHERE v.published = true AND v.language LIKE %:language%")
	List<Video> findByLanguageLike(@Param("language") String language);

	@Query("""
	    SELECT v FROM Video v 
	    WHERE v.published = true AND (
	        LOWER(v.about) LIKE LOWER(CONCAT('%', :keyword, '%'))
	        OR LOWER(v.creator) LIKE LOWER(CONCAT('%', :keyword, '%'))
	        OR LOWER(v.language) LIKE LOWER(CONCAT('%', :keyword, '%'))
	    )
	""")
	List<Video> searchByKeyword(@Param("keyword") String keyword);
	@Query("SELECT v FROM Video v WHERE v.published = true AND v.user.id = :userId AND v.about LIKE %:about%")
	List<Video> findByAboutLikeAndUserId(@Param("about") String about, @Param("userId") Long userId);

	@Query("SELECT v FROM Video v WHERE v.published = true AND v.user.id = :userId AND v.creator LIKE %:creator%")
	List<Video> findByCreatorLikeAndUserId(@Param("creator") String creator, @Param("userId") Long userId);

	@Query("SELECT v FROM Video v WHERE v.published = true AND v.user.id = :userId AND v.language LIKE %:language%")
	List<Video> findByLanguageLikeAndUserId(@Param("language") String language, @Param("userId") Long userId);

	@Query("""
	    SELECT v FROM Video v 
	    WHERE v.published = true AND v.user.id = :userId AND (
	        LOWER(v.about) LIKE LOWER(CONCAT('%', :keyword, '%'))
	        OR LOWER(v.creator) LIKE LOWER(CONCAT('%', :keyword, '%'))
	        OR LOWER(v.language) LIKE LOWER(CONCAT('%', :keyword, '%'))
	    )
	""")
	List<Video> searchByKeywordAndUserId(@Param("keyword") String keyword, @Param("userId") Long userId);


}
