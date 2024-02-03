package dev.blog.changuii.repository;

import dev.blog.changuii.entity.CommentEntity;
import dev.blog.changuii.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    public List<CommentEntity> findAllByPost(PostEntity postEntity);
}
