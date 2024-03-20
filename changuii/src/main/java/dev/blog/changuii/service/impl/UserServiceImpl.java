package dev.blog.changuii.service.impl;

import dev.blog.changuii.dao.UserDAO;
import dev.blog.changuii.dto.response.ResponseImageDTO;
import dev.blog.changuii.entity.UserEntity;
import dev.blog.changuii.service.ImageService;
import dev.blog.changuii.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final ImageService imageService;

    @Value("${application.url}")
    private String url;

    public UserServiceImpl(
            @Autowired UserDAO userDAO,
            @Autowired ImageService imageService
            ){
        this.userDAO = userDAO;
        this.imageService = imageService;
    }


    @Override
    @Transactional
    public ResponseImageDTO updateProfileImage(MultipartFile image, String email) {

        // 유저 조회
        UserEntity userEntity = this.userDAO.readUser(email)
                .orElseThrow(RuntimeException::new);
        String deleteUrl = userEntity.getProfileImage();

        // url을 가져옴
        String url = this.imageService.uploadImages(Arrays.asList(image))
                .get(0).getUrl();
        userEntity.setProfileImage(url);

        this.imageService.deleteImage(Long.parseLong(deleteUrl.replace(url + "/image/", "")));
        this.userDAO.createUser(userEntity);
        return ResponseImageDTO.builder().url(url).build();
    }
}
