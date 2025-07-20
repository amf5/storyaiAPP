
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

    @Query("SELECT new com.storyAi.story_AI.dto.BookDto(b.bookId,b.outhor, b.language, b.nameBook, b.page, b.introduction, b.about, b.coverImage,b.createdBook) " +
           "FROM Book b WHERE b.published = :published")
    List<BookDto> findByPublished(@Param("published") Boolean published);

    @Query("SELECT new com.storyAi.story_AI.dto.BookDto(b.bookId,b.outhor, b.language, b.nameBook, b.page, b.introduction, b.about, b.coverImage,b.createdBook) " +
           "FROM Book b WHERE b.user.id = :userId")
    List<BookDto> findMyBooks(@Param("userId") Long userId);
    @Query("SELECT new com.storyAi.story_AI.dto.BookDto( " +
    	       "b.bookId, b.outhor, b.language, b.nameBook, b.page, " +
    	       "b.introduction, b.about, b.coverImage, b.createdBook) " +
    	       "FROM Book b " +
    	       "WHERE b.published = true " +
    	       "AND NOT EXISTS (SELECT h FROM History h WHERE h.book.id = b.id) " +
    	       "AND b.user.id <> :excludedUserId")
    List<BookDto> findAvailableBooks(@Param("excludedUserId") Long excludedUserId);
    
    @Query("SELECT b FROM Book b WHERE b.published = true AND b.about LIKE %:about%")
    List<Book> findByAboutLike(@Param("about") String about);

    @Query("SELECT b FROM Book b WHERE b.published = true AND b.outhor LIKE %:outhor%")
    List<Book> findByOuthorLike(@Param("outhor") String outhor);

    @Query("SELECT b FROM Book b WHERE b.published = true AND b.language LIKE %:language%")
    List<Book> findByLanguageLike(@Param("language") String language);

    @Query("""
        SELECT b FROM Book b 
        WHERE b.published = true AND (
            LOWER(b.about) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(b.outhor) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(b.language) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    List<Book> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT b FROM Book b WHERE b.published = true AND b.user.id = :userId AND b.about LIKE %:about%")
    List<Book> findByAboutLikeAndUserId(@Param("about") String about, @Param("userId") Long userId);

    @Query("SELECT b FROM Book b WHERE b.published = true AND b.user.id = :userId AND b.outhor LIKE %:outhor%")
    List<Book> findByOuthorLikeAndUserId(@Param("outhor") String outhor, @Param("userId") Long userId);

    @Query("SELECT b FROM Book b WHERE b.published = true AND b.user.id = :userId AND b.language LIKE %:language%")
    List<Book> findByLanguageLikeAndUserId(@Param("language") String language, @Param("userId") Long userId);

    @Query("""
        SELECT b FROM Book b 
        WHERE b.published = true AND b.user.id = :userId AND (
            LOWER(b.about) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(b.outhor) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(b.language) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    List<Book> searchByKeywordAndUserId(@Param("keyword") String keyword, @Param("userId") Long userId);

}
