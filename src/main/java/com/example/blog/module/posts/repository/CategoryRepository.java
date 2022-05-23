package com.example.blog.module.posts.repository;

import com.example.blog.module.posts.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
