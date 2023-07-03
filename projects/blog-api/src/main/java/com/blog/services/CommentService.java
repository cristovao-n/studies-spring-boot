package com.blog.services;

import com.blog.payloads.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment (long postId, CommentDTO commentDTO);
    List<CommentDTO> getCommentsByPostId(long postId);

    CommentDTO getCommentById(long postId, long commentId);

    CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO);

    void deleteComment(long postId, long commentId);

}
