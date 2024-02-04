package dev.blog.changuii.controller;


import dev.blog.changuii.dto.CommentDTO;
import dev.blog.changuii.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;


    public CommentController(
            @Autowired CommentService commentService
    ){
      this.commentService = commentService;
    }


    @PostMapping()
    public ResponseEntity<CommentDTO> createComment(
            @RequestBody CommentDTO commentDTO
            ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.commentService.createComment(commentDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> readComment(
            @PathVariable("id") Long id
    ){
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(this.commentService.readComment(id));
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> readAllComment(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.commentService.readAllComment());
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> readAllByPostComment(
            @PathVariable("postId") Long postId
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.commentService.readAllByPostComment(postId));
    }


    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable("id") @NotNull Long id,
            @RequestBody CommentDTO commentDTO
    ){
        commentDTO.setId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.commentService.updateComment(commentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("id") Long id
    ){
        this.commentService.deleteComment(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("댓글이 성공적으로 삭제되었습니다.");
    }


}
