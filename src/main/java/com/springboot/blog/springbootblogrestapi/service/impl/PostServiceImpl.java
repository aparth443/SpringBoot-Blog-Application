package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.Payloads.PostDto;
import com.springboot.blog.springbootblogrestapi.Payloads.PostResponse;
import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.repository.CategoryRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper mapper;

    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category","id",postDto.getCategoryId()));
        //convert dto to entity
        Post post = mapToEntity(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);
        //convert entity to dto
        return mapToDTO(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir) {

        //to sort according to ascending or descending
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable = (Pageable) PageRequest.of(pageNo,pageSize, sort);

        Page<Post> postList = postRepository.findAll((org.springframework.data.domain.Pageable) pageable);
        List<Post> posts = postList.getContent();
        List<PostDto> postDtoList = new ArrayList<>();

        for(Post post: posts){
            PostDto postResponse = mapToDTO(post);
            postDtoList.add(postResponse);
        }
        //converting to postResponse
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNo(postList.getNumber());
        postResponse.setPageSize(postList.getSize());
        postResponse.setTotalElements(postList.getTotalElements());
        postResponse.setLast(postList.isLast());
        postResponse.setTotalPages(postList.getTotalPages());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category","id",postDto.getCategoryId()));
        Post post = postRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post newPost = postRepository.save(post);
        return mapToDTO(newPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));

        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream().map((post) -> mapToDTO(post)).collect(Collectors.toList());
    }

    //convert Entity to DTO
    private PostDto mapToDTO(Post post){
        return mapper.map(post,PostDto.class);
//        PostDto postResponse = new PostDto();
//        postResponse.setId(post.getId());
//        postResponse.setTitle(post.getTitle());
//        postResponse.setDescription(post.getDescription());
//        postResponse.setContent(post.getContent());
//        return postResponse;
    }

    //convert Dto to Entity
    private Post mapToEntity(PostDto postDto){
        return mapper.map(postDto,Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
//
//        return post;
    }

}
