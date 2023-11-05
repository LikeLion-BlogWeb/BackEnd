package dev.blog.changuii.controller;


import dev.blog.changuii.dto.PostDTO;
import dev.blog.changuii.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        logger.info("[createPost] "+ postDTO.toString());
        return this.postService.createPost(postDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> readPost(
            @PathVariable("id") Long id
    ){
        logger.info("[readPost] id : " + id);
        return this.postService.readPost(id);
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> readAllPost(){
        return this.postService.readAllPost();
    }

    @PutMapping
    public ResponseEntity<PostDTO> updatePost(
            @RequestBody PostDTO postDTO
    ){
        logger.info("[updatePost] "+postDTO.toString());
        return this.postService.updatePost(postDTO);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Boolean> deletePost(
            @PathVariable("id") Long id
    ){
        logger.info("[deletePost] id :" +id);
        return this.postService.deletePost(id);
    }


}
