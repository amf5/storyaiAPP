package com.storyAi.story_AI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.storyAi.story_AI.entity.User;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;


@Repository
public interface UserRepository extends JpaRepository<User,Long>{
boolean existsByEmail(String email);
Optional<User> findByEmail(String email);
void deleteByEmail(String email);
@Modifying
@Transactional
@Query("UPDATE User u SET u.verificationCode = null WHERE u.verificationCodeSentAt < CURRENT_TIMESTAMP - 5 MINUTE")
void clearExpiredVerificationCodes();
@Query("SELECT u.token FROM User u WHERE u.email = :email")
String findTokenByEmail(@Param("email") String email);

}
