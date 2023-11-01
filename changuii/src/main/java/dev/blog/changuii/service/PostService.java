package dev.blog.changuii.service;

import dev.blog.changuii.dto.PostDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {

    public ResponseEntity<PostDTO> createPost(PostDTO postDTO);
    public ResponseEntity<PostDTO> readPost(Long id);
    public ResponseEntity<List<PostDTO>> readAllPost();
    public ResponseEntity<PostDTO> updatePost(PostDTO postDTO);
    public ResponseEntity<Boolean> deletePost(Long id);
}
