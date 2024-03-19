package dev.blog.changuii.controller;


import dev.blog.changuii.dto.response.ResponseImageDTO;
import dev.blog.changuii.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(
            @Autowired ImageService imageService
    ){
        this.imageService = imageService;
    }


    @PostMapping
    public ResponseEntity<List<ResponseImageDTO>> uploadImage(
            @RequestPart("image") List<MultipartFile> images
    ){
        return ResponseEntity.status(201).body(this.imageService.upload(images));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<byte[]> downloadImage(
            @PathVariable("id") long id
            ){
        return ResponseEntity.status(200).body(this.imageService.download(id));
    }



}
