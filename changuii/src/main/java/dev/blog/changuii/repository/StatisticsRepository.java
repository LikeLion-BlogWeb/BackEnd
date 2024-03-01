package dev.blog.changuii.repository;

import dev.blog.changuii.entity.StatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<StatisticsEntity, Long> {

    Optional<StatisticsEntity> findByDateTime(LocalDate date);

}
