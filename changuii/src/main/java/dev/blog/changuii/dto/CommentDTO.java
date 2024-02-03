package dev.blog.changuii.dto;


import javax.validation.constraints.Email;

public class CommentDTO {

    private Long id;
    private String content;
    private String writeDate;
    @Email
    private String email;


}
