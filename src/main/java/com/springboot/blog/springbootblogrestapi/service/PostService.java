package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.Payloads.PostDto;
import com.springboot.blog.springbootblogrestapi.Payloads.PostResponse;

import java.util.List;


public interface PostService {

    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);
    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto, long id);
    void deletePostById(long id);

    List<PostDto> getPostsByCategory(long categoryId);

}
