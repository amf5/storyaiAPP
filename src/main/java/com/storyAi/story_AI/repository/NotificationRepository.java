package com.storyAi.story_AI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.storyAi.story_AI.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
