package dev.blog.changuii.service;

import dev.blog.changuii.dto.StatisticsDTO;

import java.time.LocalDate;
import java.util.List;

public interface StatisticsService {


    public void addViews(LocalDate date);
    public StatisticsDTO readStatisticsByLocalDate(LocalDate date);
    public List<StatisticsDTO> readAllStatistics();

}
