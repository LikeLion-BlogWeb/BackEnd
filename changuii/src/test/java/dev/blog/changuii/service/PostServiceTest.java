package dev.blog.changuii.service;


import dev.blog.changuii.service.impl.PostServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@Import({PostServiceImpl.class})
public class PostServiceTest {


    @Test
    @DisplayName("test")
    public void test(){}





}
