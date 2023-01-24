package com.blog.services.impl;

import com.blog.entities.Post;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PaginatedResponse;
import com.blog.payloads.PostDTO;
import com.blog.repositories.PostRepository;
import com.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = this.convertDTOToEntity(postDTO);
        Post savedPost = this.postRepository.save(post);
        PostDTO postResponse = this.convertEntityToDTO(savedPost);

        return postResponse;

    }

    @Override
    public PaginatedResponse<PostDTO> getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        boolean isAscendingOrder = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name());
        Sort sort = isAscendingOrder ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> paginatedPosts = this.postRepository.findAll(pageable);
        List<Post> postsInPage = paginatedPosts.getContent();
        List<PostDTO> postsDTOInPage = postsInPage.stream().map(post -> this.convertEntityToDTO(post)).collect(Collectors.toList());

        PaginatedResponse<PostDTO> paginatedPostsResponse = new PaginatedResponse<>();
        paginatedPostsResponse.setContent(postsDTOInPage);
        paginatedPostsResponse.setPageNumber(paginatedPosts.getNumber());
        paginatedPostsResponse.setPageSize(paginatedPosts.getSize());
        paginatedPostsResponse.setTotalElements(paginatedPosts.getTotalElements());
        paginatedPostsResponse.setTotalPages(paginatedPosts.getTotalPages());
        paginatedPostsResponse.setFirstPage(paginatedPosts.isFirst());
        paginatedPostsResponse.setLastPage(paginatedPosts.isLast());

        return paginatedPostsResponse;
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", id));
        return this.convertEntityToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", id));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post updatedPost = this.postRepository.save(post);
        return this.convertEntityToDTO(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", id));
        this.postRepository.delete(post);
    }


    private PostDTO convertEntityToDTO(Post post) {
        PostDTO postDTO = this.mapper.map(post, PostDTO.class);
        return postDTO;
    }

    private Post convertDTOToEntity(PostDTO postDTO) {
        Post post = this.mapper.map(postDTO, Post.class);
        return post;
    }

}
