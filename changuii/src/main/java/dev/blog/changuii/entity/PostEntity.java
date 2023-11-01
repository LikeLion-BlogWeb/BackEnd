package dev.blog.changuii.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "post")
@ToString
public class PostEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String email;

    @Column
    private LocalDateTime writeDate;


    // SQL 예약어와 동일한 컬럼 사용불가 !!
    @Column
    @Builder.Default
    @ElementCollection
    private List<String> likes = new ArrayList<>();

    @Column
    private Long views;

}
