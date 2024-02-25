package dev.blog.changuii.service.impl;

import dev.blog.changuii.dao.CommentDAO;
import dev.blog.changuii.dao.PostDAO;
import dev.blog.changuii.dao.UserDAO;
import dev.blog.changuii.dto.CommentDTO;
import dev.blog.changuii.dto.response.ResponseCommentDTO;
import dev.blog.changuii.entity.CommentEntity;
import dev.blog.changuii.entity.PostEntity;
import dev.blog.changuii.entity.UserEntity;
import dev.blog.changuii.exception.CommentNotFoundException;
import dev.blog.changuii.exception.PostNotFoundException;
import dev.blog.changuii.exception.UserNotFoundException;
import dev.blog.changuii.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDAO commentDAO;
    private final UserDAO userDAO;
    private final PostDAO postDAO;

    public CommentServiceImpl(
            @Autowired CommentDAO commentDAO,
            @Autowired PostDAO postDAO,
            @Autowired UserDAO userDAO
    ){
        this.postDAO = postDAO;
        this.userDAO = userDAO;
        this.commentDAO = commentDAO;
    }


    private UserEntity findUser(String email){
        return this.userDAO.readUser(email)
                .orElseThrow(UserNotFoundException::new);
    }

    private PostEntity findPost(Long postId){
        return this.postDAO.readPost(postId)
                .orElseThrow(PostNotFoundException::new);
    }


    @Override
    public ResponseCommentDTO createComment(CommentDTO commentDTO)
            throws UserNotFoundException, PostNotFoundException{
        CommentEntity comment = CommentDTO.toEntity(
                commentDTO,
                findUser(commentDTO.getEmail()),
                findPost(commentDTO.getPostId())
        );

        comment = this.commentDAO.createComment(comment);

        return CommentEntity.toResponseCommentDTO(comment);
    }

    @Override
    public ResponseCommentDTO readComment(Long id)
            throws CommentNotFoundException {
        return CommentEntity.toResponseCommentDTO(
                this.commentDAO.readComment(id)
                .orElseThrow(CommentNotFoundException::new));
    }

    @Override
    public List<ResponseCommentDTO> readAllComment() {
        return CommentEntity.toResponseCommentDTOs(
                this.commentDAO.readAllComment());
    }

    @Override
    public List<ResponseCommentDTO> readAllByPostComment(Long postId)
            throws PostNotFoundException{
        PostEntity post = findPost(postId);

        return CommentEntity.toResponseCommentDTOs(
                this.commentDAO.readAllByPostComment(post)
        );
    }

    @Override
    public ResponseCommentDTO updateComment(CommentDTO commentDTO)
            throws CommentNotFoundException {
        CommentEntity origin = this.commentDAO.readComment(commentDTO.getId())
                .orElseThrow(CommentNotFoundException::new);

        // Content만 수정 가능
        CommentEntity after = CommentEntity.updateComment(
                origin, commentDTO);
        after = this.commentDAO.createComment(after);

        return CommentEntity.toResponseCommentDTO(after);
    }

    @Override
    public void deleteComment(Long id)
            throws CommentNotFoundException{
        if(!this.commentDAO.deleteComment(id))
            throw new CommentNotFoundException();
    }
}
