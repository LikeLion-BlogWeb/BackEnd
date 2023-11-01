package dev.blog.changuii.service.impl;

import dev.blog.changuii.dao.PostDAO;
import dev.blog.changuii.dto.PostDTO;
import dev.blog.changuii.entity.PostEntity;
import dev.blog.changuii.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final static Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
    private final PostDAO postDAO;

    public PostServiceImpl(
            @Autowired PostDAO postDAO
    ){
        this.postDAO = postDAO;
    }

    @Override
    public ResponseEntity<PostDTO> createPost(PostDTO postDTO) {
        PostEntity postEntity = PostEntity.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .email(postDTO.getEmail())
                .writeDate(LocalDateTime.parse(postDTO.getWriteDate()))
                .likes(new ArrayList<>())
                .views(0L).build();
        postEntity = this.postDAO.createPost(postEntity);

        postDTO = PostDTO.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .email(postEntity.getEmail())
                .writeDate(postEntity.getWriteDate().toString())
                .like(postEntity.getLikes())
                .views(postEntity.getViews()).build();

        return ResponseEntity.status(201).body(postDTO);
    }

    @Override
    public ResponseEntity<PostDTO> readPost(Long id) {
        Optional<PostEntity> post = this.postDAO.readPost(id);
        if(!post.isPresent()){
            return ResponseEntity.status(400).body(null);
        }
        PostEntity postEntity = post.get();
        PostDTO postDTO = PostDTO.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .email(postEntity.getEmail())
                .writeDate(postEntity.getWriteDate().toString())
                .like(postEntity.getLikes())
                .views(postEntity.getViews()).build();


        return ResponseEntity.status(200).body(postDTO);
    }

    @Override
    public ResponseEntity<List<PostDTO>> readAllPost() {
        List<PostEntity> postEntities = this.postDAO.readAllPost();
        List<PostDTO> postDTOs = new ArrayList<>();
        for(PostEntity postEntity : postEntities){
            PostDTO postDTO = PostDTO.builder()
                    .id(postEntity.getId())
                    .title(postEntity.getTitle())
                    .content(postEntity.getContent())
                    .email(postEntity.getEmail())
                    .writeDate(postEntity.getWriteDate().toString())
                    .like(postEntity.getLikes())
                    .views(postEntity.getViews()).build();
            postDTOs.add(postDTO);
        }


        return ResponseEntity.status(200).body(postDTOs);
    }

    @Override
    public ResponseEntity<PostDTO> updatePost(PostDTO postDTO) {
        PostEntity postEntity = PostEntity.builder()
                .id(postDTO.getId())
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .email(postDTO.getEmail())
                .writeDate(LocalDateTime.parse(postDTO.getWriteDate()))
                .likes(postDTO.getLike())
                .views(postDTO.getViews()).build();

        postEntity = this.postDAO.createPost(postEntity);
        postDTO = PostDTO.builder()
                .id(postEntity.getId())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .email(postEntity.getEmail())
                .writeDate(postEntity.getWriteDate().toString())
                .like(postEntity.getLikes())
                .views(postEntity.getViews()).build();

        return ResponseEntity.status(200).body(postDTO);
    }

    @Override
    public ResponseEntity<Boolean> deletePost(Long id) {
        if(!this.postDAO.deletePost(id)){
            return ResponseEntity.status(400).body(false);
        }

        return ResponseEntity.status(200).body(true);
    }
}
