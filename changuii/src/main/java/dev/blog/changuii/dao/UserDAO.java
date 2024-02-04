package dev.blog.changuii.dao;


import dev.blog.changuii.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    public UserEntity createUser(UserEntity userEntity);
    public Optional<UserEntity> readUser(String email);
    public List<UserEntity> readAllUser();
    public boolean deleteUser(String email);
    public boolean existByEmail(String email);
}
