
package com.storyAi.story_AI.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.storyAi.story_AI.dto.BookDto;
import com.storyAi.story_AI.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT new com.storyAi.story_AI.dto.BookDto(b.bookId,b.outhor, b.language, b.nameBook, b.pages, b.introduction, b.about, b.coverImage,b.createdBook) " +
           "FROM Book b WHERE b.published = :published")
    List<BookDto> findByPublished(@Param("published") Boolean published);

    @Query("SELECT new com.storyAi.story_AI.dto.BookDto(b.bookId,b.outhor, b.language, b.nameBook, b.pages, b.introduction, b.about, b.coverImage,b.createdBook) " +
           "FROM Book b WHERE b.user.id = :userId")
    List<BookDto> findMyBooks(@Param("userId") Long userId);
    @Query("SELECT new com.storyAi.story_AI.dto.BookDto( " +
    	       "b.bookId, b.outhor, b.language, b.nameBook, b.pages, " +
    	       "b.introduction, b.about, b.coverImage, b.createdBook) " +
    	       "FROM Book b " +
    	       "WHERE b.published = true " +
    	       "AND NOT EXISTS (SELECT h FROM History h WHERE h.book.id = b.id) " +
    	       "AND b.user.id <> :excludedUserId")
    	List<BookDto> findAvailableBooks(@Param("excludedUserId") Long excludedUserId);


}
