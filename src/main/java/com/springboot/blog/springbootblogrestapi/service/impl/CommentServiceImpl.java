package com.springboot.blog.springbootblogrestapi.service.impl;


import com.springboot.blog.springbootblogrestapi.Payloads.CommentDto;
import com.springboot.blog.springbootblogrestapi.entity.Comment;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.BlogAPIException;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.repository.CommentRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }
    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtoList = new ArrayList<>();
        for(Comment comment : comments){
            commentDtoList.add(mapToDTO(comment));
        }
        return commentDtoList;
    }
    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to this post.");
        }
        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId,CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to this post.");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);

    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to this post.");
        }
        commentRepository.delete(comment);
    }


    private CommentDto mapToDTO(Comment comment){
        return mapper.map(comment,CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
//        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        return mapper.map(commentDto,Comment.class);
//        Comment comment = new Comment();
//        comment.setName(commentDto.getName());
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
//        return comment;
    }
}
