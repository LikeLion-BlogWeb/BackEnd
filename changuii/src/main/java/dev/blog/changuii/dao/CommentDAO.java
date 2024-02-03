package dev.blog.changuii.dao;


import dev.blog.changuii.entity.CommentEntity;
import dev.blog.changuii.entity.PostEntity;

import java.util.List;
import java.util.Optional;

public interface CommentDAO {

    public CommentEntity createComment(CommentEntity commentEntity);
    public Optional<CommentEntity> readComment(Long id);
    public List<CommentEntity> readAllComment();
    public List<CommentEntity> readAllByPostComment(PostEntity postEntity);
    public boolean deleteComment(Long id);

}
