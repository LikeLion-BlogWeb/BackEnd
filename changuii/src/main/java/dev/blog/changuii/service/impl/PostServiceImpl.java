package dev.blog.changuii.service.impl;

import dev.blog.changuii.dao.PostDAO;
import dev.blog.changuii.dao.StatisticsDAO;
import dev.blog.changuii.dao.UserDAO;
import dev.blog.changuii.dto.PostDTO;
import dev.blog.changuii.dto.response.ResponsePostDTO;
import dev.blog.changuii.entity.CommentEntity;
import dev.blog.changuii.entity.PostEntity;
import dev.blog.changuii.entity.StatisticsEntity;
import dev.blog.changuii.entity.UserEntity;
import dev.blog.changuii.exception.PostNotFoundException;
import dev.blog.changuii.exception.UserNotFoundException;
import dev.blog.changuii.service.PostService;
import dev.blog.changuii.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final static Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
    private final PostDAO postDAO;
    private final UserDAO userDAO;
    private final StatisticsService statisticsService;

    public PostServiceImpl(
            @Autowired StatisticsService statisticsService,
            @Autowired PostDAO postDAO,
            @Autowired UserDAO userDAO
    ){
        this.statisticsService = statisticsService;
        this.userDAO = userDAO;
        this.postDAO = postDAO;
    }

    private UserEntity findUser(String email) throws UserNotFoundException{
        UserEntity user = this.userDAO.readUser(email)
                .orElseThrow(UserNotFoundException::new);

        return user;
    }

    @Override
    public ResponsePostDTO createPost(PostDTO postDTO) throws UserNotFoundException{
        PostEntity postEntity = PostDTO
                .toEntity(postDTO, this.findUser(postDTO.getEmail()));

        postEntity = this.postDAO.createPost(postEntity);

        return PostEntity.toResponseDTO(postEntity, entity -> false);
    }

    // Comment 오름차순 정렬
    @Override
    public ResponsePostDTO readPost(Long id) throws PostNotFoundException{
        Optional<PostEntity> post = this.postDAO.readPost(id);

        PostEntity postEntity = post.orElseThrow(PostNotFoundException::new);

        // 특정 게시글의 댓글도 같이 조회

        ResponsePostDTO target = PostEntity.toResponseDTO(postEntity, entity -> true);
        target.setComments(CommentEntity.toResponseCommentDTOs(CommentEntity.descByWriteDateComment(postEntity.getComments())));

        return target;
    }

    @Override
    public List<ResponsePostDTO> readAllPost() {
        LocalDate date = LocalDate.now(ZoneId.of("Asia/Seoul"));
        statisticsService.addViews(date);

        return PostEntity.toResponseDTOs(
                this.postDAO.readAllOrderByWriteDatePost(), entity -> false);
    }

    @Override
    public ResponsePostDTO updatePost(PostDTO postDTO)throws PostNotFoundException {

        PostEntity before = this.postDAO.readPost(postDTO.getId())
                .orElseThrow(PostNotFoundException::new);

        PostEntity after = this.postDAO.createPost(
                PostEntity.updateEntity(before, postDTO)
        );

        return PostEntity.toResponseDTO(after, entity -> false);
    }

    @Override
    public void deletePost(Long id)throws PostNotFoundException {
        if(!this.postDAO.existsPost(id))
            throw new PostNotFoundException();

        this.postDAO.deletePost(id);
    }
}
