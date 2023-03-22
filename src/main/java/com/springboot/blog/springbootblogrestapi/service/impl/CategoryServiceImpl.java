package com.springboot.blog.springbootblogrestapi.service.impl;


import com.springboot.blog.springbootblogrestapi.Payloads.CategoryDto;
import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.repository.CategoryRepository;
import com.springboot.blog.springbootblogrestapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDto.class);

    }

    @Override
    public CategoryDto getCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));

        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map((category -> modelMapper.map(category,CategoryDto.class)))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category","id",id));

        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategoty(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Category","id",id)
        );
        categoryRepository.delete(category);

    }
}
