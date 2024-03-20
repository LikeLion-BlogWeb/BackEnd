package dev.blog.changuii.service;


import dev.blog.changuii.dto.response.ResponseImageDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    public ResponseImageDTO updateProfileImage(MultipartFile image, String email);


}
