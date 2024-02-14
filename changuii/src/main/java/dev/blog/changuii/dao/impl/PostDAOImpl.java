package dev.blog.changuii.dao.impl;

import dev.blog.changuii.dao.PostDAO;
import dev.blog.changuii.entity.PostEntity;
import dev.blog.changuii.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostDAOImpl implements PostDAO {

    private final static Logger logger = LoggerFactory.getLogger(PostDAOImpl.class);
    private final PostRepository postRepository;

    public PostDAOImpl(
            @Autowired PostRepository postRepository
    ){
        this.postRepository = postRepository;
    }

    @Override
    public PostEntity createPost(PostEntity postEntity) {
        return this.postRepository.save(postEntity);
    }

    @Override
    public Optional<PostEntity> readPost(Long id) {
        return this.postRepository.findById(id);
    }

    @Override
    public List<PostEntity> readAllPost() {
        return this.postRepository.findAll();
    }

    @Override
    public List<PostEntity> readAllOrderByWriteDatePost() {
        return this.postRepository.findAllByOrderByWriteDateDesc();
    }

    @Override
    public void deletePost(Long id) {
        this.postRepository.deleteById(id);
    }

    @Override
    public boolean existsPost(Long id) {
        return this.postRepository.existsById(id);
    }
}
