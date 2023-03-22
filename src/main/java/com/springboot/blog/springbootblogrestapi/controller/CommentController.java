package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.Payloads.CommentDto;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(
        name = "CRUD REST APIs for Comment Resource"
)
public class CommentController {
    @Autowired
    CommentService commentService;

    @Operation(
            summary = "Create Comment REST API",
            description = "Create Comment rest API is used to save comment to database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HttpStatus 201 Created"
    )
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId, @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Comments By Post Id REST API",
            description = "Get comments by post id rest api is used to get comments of particular post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 Success"
    )
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(value = "postId") long postId){
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId),HttpStatus.OK);
    }

    @Operation(
            summary = "Get Comment By Comment Id REST API",
            description = "Get comments by Comment id rest api is used to get comment from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 Success"
    )
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId")long postId,@PathVariable(value = "id") long id){
        return new ResponseEntity<>(commentService.getCommentById(postId,id),HttpStatus.OK);
    }

    @Operation(
            summary = "Update Comment By Comment Id REST API",
            description = "Update comment by Comment id rest api is used to update comment from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 Success"
    )
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId")long postId,@PathVariable(value = "id") long id,@Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateComment(postId,id,commentDto),HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Comment By Comment Id REST API",
            description = "Delete comments by Comment id rest api is used to delete comment from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HttpStatus 200 Success"
    )
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId")long postId,@PathVariable(value = "id") long id){
        commentService.deleteComment(postId,id);
        return new ResponseEntity<>("Comment deleted successfully.",HttpStatus.OK);
    }

}
