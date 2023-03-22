package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.Payloads.CategoryDto;
import com.springboot.blog.springbootblogrestapi.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(
        name = "CRUD REST APIs for Category Resource"
)
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    //Build Add Category Rest API
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create Category REST API",
            description = "Create Category rest API is used to save Category to database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HttpStatus 201 Created"
    )
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    //Get Category Rest API
    @GetMapping("/{id}")
    @Operation(
            summary = "Get Category By category Id REST API",
            description = "Get category by category id rest api is used to get category from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 Success"
    )
    public ResponseEntity<CategoryDto> getCategory(@PathVariable long id){
        return new ResponseEntity<>(categoryService.getCategory(id),HttpStatus.OK);
    }

    //Get All Categories Rest API
    @GetMapping
    @Operation(
            summary = "Get All Categories REST API",
            description = "Get All Categories rest api is used to get categories from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 Success"
    )
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(),HttpStatus.OK);
    }

    //Update Category Rest API

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Update Category By Category Id REST API",
            description = "Update category by category id rest api is used to update category to database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 Success"
    )
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable long id){
        return new ResponseEntity<>(categoryService.updateCategory(categoryDto,id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )

    @Operation(
            summary = "Delete Category By category Id REST API",
            description = "Delete category by category id rest api is used to delete category from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 Success"
    )
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long id){
        categoryService.deleteCategoty(id);
        return new ResponseEntity<>("Category deleted successfully",HttpStatus.OK);
    }

}
