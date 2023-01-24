package com.blog.controllers;

import com.blog.payloads.CommentDTO;
import com.blog.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    private CommentService commentService;
    public CommentController (CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(name = "postId") long postId, @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(this.commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }
}
