package dev.blog.changuii.dao;


import dev.blog.changuii.entity.UserEntity;

import java.util.List;

public interface UserDAO {

    public UserEntity createUser(UserEntity userEntity);
    public UserEntity readUser(String email);
    public List<UserEntity> readAllUser();
    public boolean deleteUser(String email);
    public boolean existByEmail(String email);
}
