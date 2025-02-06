package com.storyAi.story_AI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.storyAi.story_AI.entity.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
Optional<Role> findByRoleName(String roleName);
}
