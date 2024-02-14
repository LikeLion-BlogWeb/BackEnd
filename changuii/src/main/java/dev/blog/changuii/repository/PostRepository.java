package dev.blog.changuii.repository;


import dev.blog.changuii.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    public List<PostEntity> findAllByOrderByWriteDateDesc();
}
