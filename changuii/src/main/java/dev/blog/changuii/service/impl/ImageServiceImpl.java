package dev.blog.changuii.service.impl;

import dev.blog.changuii.dao.ImageDAO;
import dev.blog.changuii.dto.response.ResponseImageDTO;
import dev.blog.changuii.entity.ImageEntity;
import dev.blog.changuii.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageDAO imageDAO;
    private final String url = "https://likelion-blog-api.p-e.kr/image/download/";

    public ImageServiceImpl(
            @Autowired ImageDAO imageDAO
    ){
        this.imageDAO = imageDAO;
    }

    @Override
    public List<ResponseImageDTO> upload(List<MultipartFile> images) {
        return images.stream()
                .map(a->{
                    ImageEntity image;
                    try {
                        image = this.imageDAO.saveImage(ImageEntity.builder().image(a.getBytes()).build());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return ResponseImageDTO.builder().url(this.url+image.getId()).build();
                }).collect(Collectors.toList());
    }

    @Override
    public byte[] download(long id) {
        return this.imageDAO.readImage(id).get().getImage();
    }
}
