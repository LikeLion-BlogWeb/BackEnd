package dev.blog.changuii.service;


import dev.blog.changuii.dto.CommentDTO;
import dev.blog.changuii.dto.response.ResponseCommentDTO;
import dev.blog.changuii.exception.CommentNotFoundException;
import dev.blog.changuii.exception.PostNotFoundException;
import dev.blog.changuii.exception.UserNotFoundException;

import java.util.List;

public interface CommentService {

    public ResponseCommentDTO createComment(CommentDTO commentDTO) throws UserNotFoundException, PostNotFoundException;
    public ResponseCommentDTO readComment(Long id) throws CommentNotFoundException;
    public List<ResponseCommentDTO> readAllComment();
    public List<ResponseCommentDTO> readAllByPostComment(Long postId)  throws PostNotFoundException;
    public ResponseCommentDTO updateComment(CommentDTO commentDTO) throws CommentNotFoundException ;
    public void deleteComment(Long id) throws CommentNotFoundException;


}
