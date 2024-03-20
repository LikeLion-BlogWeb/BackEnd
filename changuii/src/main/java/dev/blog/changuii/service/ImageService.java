package dev.blog.changuii.service;


import dev.blog.changuii.dto.response.ResponseImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    public List<ResponseImageDTO> uploadImages(List<MultipartFile> images);
    public byte[] downloadImage(long id);
    public boolean deleteImage(long id);
}
