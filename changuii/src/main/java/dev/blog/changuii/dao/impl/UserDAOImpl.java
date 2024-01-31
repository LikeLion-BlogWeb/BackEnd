package dev.blog.changuii.dao.impl;

import dev.blog.changuii.dao.UserDAO;
import dev.blog.changuii.entity.UserEntity;
import dev.blog.changuii.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private final UserRepository userRepository;

    public UserDAOImpl(@Autowired  UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public UserEntity createUser(UserEntity userEntity) {
        return this.userRepository.save(userEntity);
    }

    @Override
    public UserEntity readUser(String email) {
        return this.userRepository.findByEmail(email).get();
    }

    @Override
    public List<UserEntity> readAllUser() {
        return this.userRepository.findAll();
    }

    @Override
    public boolean deleteUser(String email) {
        return this.userRepository.deleteByEmail(email) > 0 ? true : false;
    }

    @Override
    public boolean existByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }
}
