package dev.blog.changuii.entity;

import dev.blog.changuii.dto.StatisticsDTO;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatisticsEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long views;

    private LocalDate dateTime;


    public void addViews(){
        this.views++;
    }

    public static StatisticsDTO toDTO(StatisticsEntity statisticsEntity){
        return StatisticsDTO.builder()
                .views(statisticsEntity.getViews())
                .date(statisticsEntity.getDateTime().toString())
                .build();
    }

    public static List<StatisticsDTO> toDTOs(List<StatisticsEntity> statisticsEntities){
        List<StatisticsDTO> dtos = new ArrayList<>();
        statisticsEntities.forEach(s->dtos.add(toDTO(s)));
        return dtos;
    }


}
