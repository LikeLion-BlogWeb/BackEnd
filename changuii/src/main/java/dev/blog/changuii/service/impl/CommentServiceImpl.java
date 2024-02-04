package dev.blog.changuii.service.impl;

import dev.blog.changuii.dao.CommentDAO;
import dev.blog.changuii.dao.PostDAO;
import dev.blog.changuii.dao.UserDAO;
import dev.blog.changuii.dto.CommentDTO;
import dev.blog.changuii.dto.PostDTO;
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
    public CommentDTO createComment(CommentDTO commentDTO) {
        CommentEntity comment = CommentEntity.DtoToEntity(
                commentDTO,
                findUser(commentDTO.getEmail()),
                findPost(commentDTO.getPostId())
        );

        comment = this.commentDAO.createComment(comment);

        return CommentDTO.EntityToDTO(comment);
    }

    @Override
    public CommentDTO readComment(Long id) {
        return CommentDTO.EntityToDTO(
                this.commentDAO.readComment(id)
                .orElseThrow(CommentNotFoundException::new));
    }

    @Override
    public List<CommentDTO> readAllComment() {
        return CommentDTO.EntityListToDTOList(
                this.commentDAO.readAllComment());
    }

    @Override
    public List<CommentDTO> readAllByPostComment(Long postId) {
        PostEntity post = findPost(postId);

        return CommentDTO.EntityListToDTOList(
                this.commentDAO.readAllByPostComment(post)
        );
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO) {
        CommentEntity before = this.commentDAO.readComment(commentDTO.getId())
                .orElseThrow(CommentNotFoundException::new);

        // Content만 수정 가능
        CommentEntity after = CommentEntity.updateComment(before,
                CommentEntity.DtoToEntity(commentDTO, new UserEntity(), new PostEntity()));
        after = this.commentDAO.createComment(after);

        return CommentDTO.EntityToDTO(after);
    }

    @Override
    public void deleteComment(Long id) {
        if(!this.commentDAO.deleteComment(id))
            throw new CommentNotFoundException();
    }
}
