package com.blog.services;

import com.blog.payloads.PaginatedResponse;
import com.blog.payloads.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    PaginatedResponse<PostDTO> getAllPosts(int pageNumber, int pageSize);

    PostDTO getPostById(long id);

    PostDTO updatePost(PostDTO postDTO, long id);

    void deletePost(long id);

}
