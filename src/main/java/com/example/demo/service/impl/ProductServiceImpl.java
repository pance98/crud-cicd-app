package com.example.demo.service.impl;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.exceptions.InvalidProductIdException;
import com.example.demo.repostitory.CategoryRepository;
import com.example.demo.repostitory.ProductRepository;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService
{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository)
    {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Product> listAllProducts()
    {
        return this.productRepository.findAll();
    }

    @Override
    public Product findById(Long id)
    {
        return this.productRepository.findById(id)
                .orElseThrow(InvalidProductIdException::new);
    }

    @Override
    public Product create(String name, Double price, Integer quantity, List<Long> categoryIds)
    {
        List<Category>categories = this.categoryRepository.findAllById(categoryIds);
        Product product = new Product(name,
                price, quantity, categories);
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, String name, Double price, Integer quantity, List<Long> categoryIds)
    {
        Product product = this.findById(id);
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        List<Category> categories = this.categoryRepository.findAllById(categoryIds);
        product.setCategories(categories);

        return this.productRepository.save(product);
    }

    @Override
    public Product delete(Long id)
    {
        Product product = this.findById(id);
        this.productRepository.delete(product);
        return product;
    }

    @Override
    public List<Product> listProductsByNameAndCategory(String name, Long categoryId)
    {
        Category category =  categoryId != null ? this.categoryRepository.findById(categoryId)
                .orElse((Category) null): null;
        String nameLike = "%"
                + name + "%";
        if ( name != null && category != null)
        {
            return this.productRepository.findAllByNameLikeAndCategoriesContaining(nameLike, category);
        }
        else if ( name != null )
        {
            return this.productRepository.findAllByNameLike(nameLike);
        }
        else if ( category != null)
        {
            return this.productRepository.findAllByCategoriesContaining(category);
        }
        else
        {
            return this.productRepository.findAll();
        }
    }
}