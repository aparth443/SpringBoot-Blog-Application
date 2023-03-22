package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.Payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategory(long categoryId);
    List<CategoryDto> getAllCategories();
    CategoryDto updateCategory(CategoryDto categoryDto, long id);
    void deleteCategoty(long id);

}
