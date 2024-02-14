package dev.blog.changuii.service.impl;

import dev.blog.changuii.dao.PostDAO;
import dev.blog.changuii.dao.UserDAO;
import dev.blog.changuii.dto.CommentDTO;
import dev.blog.changuii.dto.PostDTO;
import dev.blog.changuii.entity.CommentEntity;
import dev.blog.changuii.entity.PostEntity;
import dev.blog.changuii.entity.UserEntity;
import dev.blog.changuii.exception.PostNotFoundException;
import dev.blog.changuii.exception.UserNotFoundException;
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
    private final UserDAO userDAO;

    public PostServiceImpl(
            @Autowired PostDAO postDAO,
            @Autowired UserDAO userDAO
    ){
        this.userDAO = userDAO;
        this.postDAO = postDAO;
    }

    private UserEntity findUser(String email) throws UserNotFoundException{
        UserEntity user = this.userDAO.readUser(email)
                .orElseThrow(UserNotFoundException::new);

        return user;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) throws UserNotFoundException{

        PostEntity postEntity = PostEntity
                .initEntity(postDTO, this.findUser(postDTO.getEmail()));

        postEntity = this.postDAO.createPost(postEntity);

        return PostDTO.entityToDTO(postEntity);
    }

    // Comment 오름차순 정렬
    @Override
    public PostDTO readPost(Long id) throws PostNotFoundException{
        Optional<PostEntity> post = this.postDAO.readPost(id);

        PostEntity postEntity = post.orElseThrow(PostNotFoundException::new);

        // 특정 게시글의 댓글도 같이 조회

        PostDTO target = PostDTO.entityToDTO(postEntity);
        target.setComments(CommentDTO.EntityListToDTOList(CommentEntity.descByWriteDateComment(postEntity.getComments())));

        return target;
    }

    @Override
    public List<PostDTO> readAllPost() {

        return PostDTO.entityListToDTOList(
                this.postDAO.readAllOrderByWriteDatePost()
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
