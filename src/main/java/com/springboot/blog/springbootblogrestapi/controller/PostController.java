package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.Payloads.PostDto;
import com.springboot.blog.springbootblogrestapi.Payloads.PostResponse;
import com.springboot.blog.springbootblogrestapi.service.impl.PostServiceImpl;
import com.springboot.blog.springbootblogrestapi.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD REST APIs for Post Resource"
)
public class PostController {
    @Autowired
    PostServiceImpl postService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post rest API is used to save post to database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HttpStatus 201 Created"
    )
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get All Posts REST API",
            description = "Get All Posts rest API is used to get all the posts from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 Success"
    )
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
                                                    @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false)int pageSize,
                                                    @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false)String sortBy,
                                                    @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false)String sortDir){
        return new ResponseEntity<>(postService.getAllPosts(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @Operation(
            summary = "Get Post by Id REST API",
            description = "Get post by id rest API is used to get the post with given id from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 Success"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }

    @Operation(
            summary = "Update Post REST API",
            description = "Update Post rest API is used to update the post to database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 Success"
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable(name = "id") long id){
        return new ResponseEntity<>(postService.updatePost(postDto,id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @Operation(
            summary = "Delete Post By Id REST API",
            description = "Delete post rest api is used to delete post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 Success"
    )
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post with id" + id + " deleted successfully",HttpStatus.OK);
    }

    @Operation(
            summary = "Get Posts By Category REST API",
            description = "Get All Posts by Category rest API is used to get all the posts for particular category from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 Success"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") long id){
        return new ResponseEntity<>(postService.getPostsByCategory(id),HttpStatus.OK);
    }
}
