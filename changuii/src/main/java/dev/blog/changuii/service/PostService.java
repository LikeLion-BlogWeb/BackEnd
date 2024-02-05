package dev.blog.changuii.service;

import dev.blog.changuii.dto.PostDTO;
import dev.blog.changuii.exception.PostNotFoundException;
import dev.blog.changuii.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {

    public PostDTO createPost(PostDTO postDTO) throws UserNotFoundException;
    public PostDTO readPost(Long id)throws PostNotFoundException;
    public List<PostDTO> readAllPost();
    public PostDTO updatePost(PostDTO postDTO)throws PostNotFoundException;
    public void deletePost(Long id)throws PostNotFoundException;
}
