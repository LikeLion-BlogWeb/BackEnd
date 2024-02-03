package dev.blog.changuii.dao.impl;


import dev.blog.changuii.dao.CommentDAO;
import dev.blog.changuii.entity.CommentEntity;
import dev.blog.changuii.entity.PostEntity;
import dev.blog.changuii.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentDAOImpl implements CommentDAO {
    private final CommentRepository commentRepository;

    public CommentDAOImpl(
            @Autowired CommentRepository commentRepository
    ){
      this.commentRepository = commentRepository;
    }


    @Override
    public CommentEntity createComment(CommentEntity commentEntity) {
        return this.commentRepository.save(commentEntity);
    }

    @Override
    public Optional<CommentEntity> readComment(Long id) {
        return this.commentRepository.findById(id);
    }

    @Override
    public List<CommentEntity> readAllComment() {
        return this.commentRepository.findAll();
    }

    @Override
    public List<CommentEntity> readAllByPostComment(PostEntity postEntity) {
        return this.commentRepository.findAllByPost(postEntity);
    }

    @Override
    public boolean deleteComment(Long id) {

        if(!this.commentRepository.existsById(id)) return false;
        else{
            this.commentRepository.deleteById(id);
            return true;
        }

    }
}
