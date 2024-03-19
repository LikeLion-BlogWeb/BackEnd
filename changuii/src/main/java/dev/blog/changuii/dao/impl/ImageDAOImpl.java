package dev.blog.changuii.dao.impl;

import dev.blog.changuii.dao.ImageDAO;
import dev.blog.changuii.entity.ImageEntity;
import dev.blog.changuii.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class ImageDAOImpl implements ImageDAO {


    private final ImageRepository imageRepository;

    public ImageDAOImpl(
            @Autowired ImageRepository imageRepository
    ){
        this.imageRepository = imageRepository;
    }

    @Override
    public ImageEntity saveImage(ImageEntity image) {
        return this.imageRepository.save(image);
    }

    @Override
    public Optional<ImageEntity> readImage(long id) {
        return this.imageRepository.findById(id);
    }
}
