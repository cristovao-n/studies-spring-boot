package com.blog.services.impl;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exceptions.BlogAPIException;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDTO;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl (CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = this.convertDTOToEntity(commentDTO);
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", postId));
        comment.setPost(post);
        Comment savedComment = this.commentRepository.save(comment);
        return this.convertEntityToDTO(savedComment);

    }

    @Override
    public List<CommentDTO> getCommentsByPostId(long postId) {
        this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", postId));
        List<Comment> comments = this.commentRepository.findByPostId(postId);
        List<CommentDTO> commentsDTO = comments.stream().map(comment -> this.convertEntityToDTO(comment)).collect(Collectors.toList());
        return commentsDTO;
    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
        Comment comment = this.validateEntities(postId, commentId);
        return this.convertEntityToDTO(comment);
    }

    @Override
    public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO) {
        Comment comment = this.validateEntities(postId, commentId);

        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        Comment updatedComment = this.commentRepository.save(comment);
        return this.convertEntityToDTO(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Comment comment = this.validateEntities(postId, commentId);
        this.commentRepository.delete(comment);
    }

    private Comment validateEntities(long postId, long commentId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", postId));
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", commentId));

        boolean commentBelongsToPost = comment.getPost().equals(post);
        if (!commentBelongsToPost) {
            throw new BlogAPIException("Wrong post_id", "Comment does not belong to this post");
        }
        return comment;
    }

    private CommentDTO convertEntityToDTO(Comment comment) {
        CommentDTO commentDTO = this.mapper.map(comment, CommentDTO.class);
        return commentDTO;
    }

    private Comment convertDTOToEntity(CommentDTO commentDTO) {
        Comment comment = this.mapper.map(commentDTO, Comment.class);
        return comment;
    }
}
