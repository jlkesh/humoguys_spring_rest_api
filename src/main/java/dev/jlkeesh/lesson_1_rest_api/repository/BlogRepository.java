package dev.jlkeesh.lesson_1_rest_api.repository;

import dev.jlkeesh.lesson_1_rest_api.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, String> {
    List<Blog> getAllByDeletedFalse();
}