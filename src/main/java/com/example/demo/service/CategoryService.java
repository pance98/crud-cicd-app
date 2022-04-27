package com.example.demo.service;

import com.example.demo.model.Category;

import java.util.List;
import java.util.Optional;


public interface CategoryService {

    Category findById(Long id);

    List<Category> listAll();

    Category create(String name);
}