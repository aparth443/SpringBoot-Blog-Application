package com.springboot.blog.springbootblogrestapi.repository;

import com.springboot.blog.springbootblogrestapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
