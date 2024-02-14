package dev.blog.changuii.dao;


import dev.blog.changuii.entity.PostEntity;

import java.util.List;
import java.util.Optional;

public interface PostDAO {
    public PostEntity createPost(PostEntity postEntity);
    public Optional<PostEntity> readPost(Long id);
    public List<PostEntity> readAllPost();
    public List<PostEntity> readAllOrderByWriteDatePost();
    public void deletePost(Long id);
    public boolean existsPost(Long id);


}
