package dev.blog.changuii.controller;


import dev.blog.changuii.dto.PostDTO;
import dev.blog.changuii.exception.PostNotFoundException;
import dev.blog.changuii.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private final static Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    public PostController(
            @Autowired PostService postService
    ){
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(
            @RequestBody PostDTO postDTO
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.postService.createPost(postDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> readPost(
            @PathVariable("id") Long id
    ){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.postService.readPost(id));
        }catch (PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PostDTO.builder().title(e.getMessage()).build());
        }
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> readAllPost(){
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.readAllPost());
    }

    @PutMapping
    public ResponseEntity<PostDTO> updatePost(
            @RequestBody PostDTO postDTO
    ){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.postService.updatePost(postDTO));
        }catch (PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PostDTO.builder().title(e.getMessage()).build());
        }
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Boolean> deletePost(
            @PathVariable("id") Long id
    ){
        try {
            this.postService.deletePost(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }catch (PostNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }


}
