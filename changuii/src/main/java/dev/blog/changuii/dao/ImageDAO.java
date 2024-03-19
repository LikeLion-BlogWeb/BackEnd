package dev.blog.changuii.dao;

import dev.blog.changuii.entity.ImageEntity;

import java.util.Optional;

public interface ImageDAO {

    public ImageEntity saveImage(ImageEntity image);
    public Optional<ImageEntity> readImage(long id);
}
