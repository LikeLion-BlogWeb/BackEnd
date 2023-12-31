package dev.blog.changuii.dao;


import dev.blog.changuii.entity.PostEntity;

import java.util.List;
import java.util.Optional;

public interface PostDAO {
    public PostEntity createPost(PostEntity postEntity);
    public Optional<PostEntity> readPost(Long id);
    public List<PostEntity> readAllPost();
    public boolean deletePost(Long id);


}
