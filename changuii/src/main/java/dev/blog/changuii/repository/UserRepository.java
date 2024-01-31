package dev.blog.changuii.repository;

import dev.blog.changuii.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByEmail(String email);
    public boolean existsByEmail(String email);
    public int deleteByEmail(String email);
}
