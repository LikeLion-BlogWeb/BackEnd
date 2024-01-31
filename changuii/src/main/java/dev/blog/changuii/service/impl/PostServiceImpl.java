package dev.blog.changuii.service.impl;

import dev.blog.changuii.dao.PostDAO;
import dev.blog.changuii.dto.PostDTO;
import dev.blog.changuii.entity.PostEntity;
import dev.blog.changuii.exception.PostNotFoundException;
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
    public PostDTO createPost(PostDTO postDTO) {
        PostEntity postEntity = PostEntity.DtoToEntity(postDTO);

        postEntity = this.postDAO.createPost(postEntity);

        return PostDTO.entityToDTO(postEntity);
    }

    @Override
    public PostDTO readPost(Long id) throws PostNotFoundException{
        Optional<PostEntity> post = this.postDAO.readPost(id);

        PostEntity postEntity = post.orElseThrow(PostNotFoundException::new);

        return PostDTO.entityToDTO(postEntity);
    }

    @Override
    public List<PostDTO> readAllPost() {

        return PostDTO.entityListToDTOList(
                this.postDAO.readAllPost()
        );
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO)throws PostNotFoundException {

        PostEntity before = this.postDAO.readPost(postDTO.getId())
                .orElseThrow(PostNotFoundException::new);

        PostEntity after = this.postDAO.createPost(
                PostEntity.updateEntity(before, postDTO)
        );

        return PostDTO.entityToDTO(after);
    }

    @Override
    public void deletePost(Long id)throws PostNotFoundException {
        if(!this.postDAO.existsPost(id))
            throw new PostNotFoundException();

        this.postDAO.deletePost(id);
    }
}
