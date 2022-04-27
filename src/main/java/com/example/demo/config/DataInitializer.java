package com.example.demo.config;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class DataInitializer {

    public static final String ADMIN = "admin";

    private final UserService userService;

    private final CategoryService categoryService;

    private final ProductService productService;

    public DataInitializer(UserService userService, CategoryService categoryService, ProductService productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }


    @PostConstruct
    public void initData() {
        User admin = this.userService.create(ADMIN, ADMIN, Role.ROLE_ADMIN);

        for (int i = 1; i < 6; i++) {
            this.categoryService.create("Category " + i);
        }

        for (int i = 1; i < 11; i++) {
            this.productService.create("Product " + i, 20.9 * i, i, Stream.of(1L, i % 5L + 1).collect(Collectors.toList()));
        }
    }
}