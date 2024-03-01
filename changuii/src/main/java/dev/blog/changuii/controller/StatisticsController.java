package dev.blog.changuii.controller;


import dev.blog.changuii.dto.StatisticsDTO;
import dev.blog.changuii.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(
            @Autowired StatisticsService statisticsService
    ){
        this.statisticsService = statisticsService;
    }


    @GetMapping
    public ResponseEntity<List<StatisticsDTO>> readAllStatistics(){
        return ResponseEntity.status(200).body(this.statisticsService.readAllStatistics());
    }

}
