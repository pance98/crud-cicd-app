package com.example.demo.service.impl;

import com.example.demo.model.Category;
import com.example.demo.model.exceptions.InvalidCategoryIdException;
import com.example.demo.repostitory.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findById(Long id)
    {
        return this.categoryRepository.findById(id)
                .orElseThrow(InvalidCategoryIdException::new);
    }

    @Override
    public List<Category> listAll()
    {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category create(String name)
    {
        Category category = new Category(name);
        return this.categoryRepository.save(category);
    }
}
