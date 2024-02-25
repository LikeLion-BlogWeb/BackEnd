package dev.blog.changuii.controller;


import dev.blog.changuii.dto.PostDTO;
import dev.blog.changuii.dto.ResponsePostDTO;
import dev.blog.changuii.exception.PostNotFoundException;
import dev.blog.changuii.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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

    // PostNotFoundException은 ExceptionAdvisor에서 모두 처리

    @PostMapping
    public ResponseEntity<ResponsePostDTO> createPost(
            @Valid @RequestBody PostDTO postDTO
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.postService.createPost(postDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePostDTO> readPost(
            @PathVariable("id") Long id
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.readPost(id));
    }

    @GetMapping
    public ResponseEntity<List<ResponsePostDTO>> readAllPost(){
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.readAllPost());
    }

    // todo validation 어떻게 할지 고민해봐야함.
    @PutMapping
    public ResponseEntity<ResponsePostDTO> updatePost(
            @RequestBody PostDTO postDTO
    ){
        return ResponseEntity.status(HttpStatus.OK).body(this.postService.updatePost(postDTO));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Boolean> deletePost(
            @PathVariable("id") Long id
    ){
        this.postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }


}
