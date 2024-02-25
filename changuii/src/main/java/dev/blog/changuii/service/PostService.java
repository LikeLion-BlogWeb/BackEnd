package dev.blog.changuii.service;

import dev.blog.changuii.dto.PostDTO;
import dev.blog.changuii.dto.ResponsePostDTO;
import dev.blog.changuii.exception.PostNotFoundException;
import dev.blog.changuii.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {

    public ResponsePostDTO createPost(PostDTO postDTO) throws UserNotFoundException;
    public ResponsePostDTO readPost(Long id)throws PostNotFoundException;
    public List<ResponsePostDTO> readAllPost();
    public ResponsePostDTO updatePost(PostDTO postDTO)throws PostNotFoundException;
    public void deletePost(Long id)throws PostNotFoundException;
}
