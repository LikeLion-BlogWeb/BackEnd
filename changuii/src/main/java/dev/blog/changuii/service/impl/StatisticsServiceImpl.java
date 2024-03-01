package dev.blog.changuii.service.impl;

import dev.blog.changuii.dao.StatisticsDAO;
import dev.blog.changuii.dto.StatisticsDTO;
import dev.blog.changuii.entity.StatisticsEntity;
import dev.blog.changuii.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsDAO statisticsDAO;

    public StatisticsServiceImpl(
            @Autowired StatisticsDAO statisticsDAO
    ){
        this.statisticsDAO = statisticsDAO;
    }


    @Override
    public void addViews(LocalDate date) {
        Optional<StatisticsEntity> statisticsEntity
                = this.statisticsDAO.readStatisticsByLocalDate(date);

        StatisticsEntity statistics;
        if(!statisticsEntity.isPresent()){
            statistics = this.statisticsDAO.createStatistics(
                    StatisticsEntity.builder().views(0L).dateTime(date).build()
            );
        }else{
            statistics = statisticsEntity.get();
        }
        statistics.addViews();
        this.statisticsDAO.createStatistics(statistics);
    }

    @Override
    public StatisticsDTO readStatisticsByLocalDate(LocalDate date) {
        return null;
    }

    @Override
    public List<StatisticsDTO> readAllStatistics() {


        return StatisticsEntity.toDTOs(this.statisticsDAO.readAllStatistics());
    }
}
