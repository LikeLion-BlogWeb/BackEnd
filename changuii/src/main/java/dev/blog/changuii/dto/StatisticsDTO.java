package dev.blog.changuii.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StatisticsDTO {
    public long views;
    public String date;
}
