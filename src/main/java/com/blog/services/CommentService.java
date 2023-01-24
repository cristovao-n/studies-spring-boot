package com.blog.services;

import com.blog.payloads.CommentDTO;

public interface CommentService {

    CommentDTO createComment (long postId, CommentDTO commentDTO);

}
