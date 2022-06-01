package com.example.blog.module.posts.service;

import com.example.blog.module.posts.model.Category;
import com.example.blog.module.posts.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category registerCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    public List<Category> findAllCategories() {
        return this.categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
