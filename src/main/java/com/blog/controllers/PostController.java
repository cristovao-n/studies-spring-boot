package com.blog.controllers;

import com.blog.payloads.PaginatedResponse;
import com.blog.payloads.PostDTO;
import com.blog.services.PostService;
import com.blog.utils.PaginationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(this.postService.createPost(postDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<PostDTO>> getAllPosts(
            @RequestParam(name = "pageNumber", defaultValue = PaginationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = PaginationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = PaginationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = PaginationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return ResponseEntity.ok(this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(this.postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(this.postService.updatePost(postDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        this.postService.deletePost(id);
        return ResponseEntity.ok("Post entity deleted successfully.");
    }

}
