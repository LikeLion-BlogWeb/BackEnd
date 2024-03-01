package dev.blog.changuii.dao.impl;

import dev.blog.changuii.dao.StatisticsDAO;
import dev.blog.changuii.entity.StatisticsEntity;
import dev.blog.changuii.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public class StatisticsDAOImpl implements StatisticsDAO {

    private final StatisticsRepository statisticsRepository;

    public StatisticsDAOImpl(
            @Autowired StatisticsRepository statisticsRepository
    ){
        this.statisticsRepository = statisticsRepository;
    }


    @Override
    public StatisticsEntity createStatistics(StatisticsEntity statisticsEntity) {
        return this.statisticsRepository.save(statisticsEntity);
    }

    @Override
    public Optional<StatisticsEntity> readStatisticsByLocalDate(LocalDate date) {
        return this.statisticsRepository.findByDateTime(date);
    }

    @Override
    public List<StatisticsEntity> readAllStatistics() {
        return this.statisticsRepository.findAll();
    }


}
