package dev.blog.changuii.service;


import dev.blog.changuii.dto.CommentDTO;
import dev.blog.changuii.exception.CommentNotFoundException;
import dev.blog.changuii.exception.PostNotFoundException;
import dev.blog.changuii.exception.UserNotFoundException;

import java.util.List;

public interface CommentService {

    public CommentDTO createComment(CommentDTO commentDTO) throws UserNotFoundException, PostNotFoundException;
    public CommentDTO readComment(Long id) throws CommentNotFoundException;
    public List<CommentDTO> readAllComment();
    public List<CommentDTO> readAllByPostComment(Long postId)  throws PostNotFoundException;
    public CommentDTO updateComment(CommentDTO commentDTO) throws CommentNotFoundException ;
    public void deleteComment(Long id) throws CommentNotFoundException;


}
