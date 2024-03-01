package dev.blog.changuii.dao;

import dev.blog.changuii.entity.StatisticsEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StatisticsDAO {

    public StatisticsEntity createStatistics(StatisticsEntity statisticsEntity);

    public Optional<StatisticsEntity> readStatisticsByLocalDate(LocalDate date);

    public List<StatisticsEntity> readAllStatistics();

}
