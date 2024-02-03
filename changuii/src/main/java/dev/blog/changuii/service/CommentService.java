package dev.blog.changuii.service;


import dev.blog.changuii.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    public CommentDTO createComment(CommentDTO commentDTO);
    public CommentDTO readComment(Long id);
    public List<CommentDTO> readAllComment();
    public List<CommentDTO> readAllByPostComment(Long postId);
    public CommentDTO updateComment(CommentDTO commentDTO);
    public void deleteComment(Long id);


}
