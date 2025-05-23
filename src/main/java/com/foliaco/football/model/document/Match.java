package com.foliaco.football.model.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "partidos")
public class Match {

    @Id
    private String id;

    @Field(name = "temporada")
    private String season;

    @Field(name = "competicion")
    private String competition;

    @Field(name = "equipo_local_id")
    private String localTeamId;

    @Field(name = "equipo_visitante_id")
    private String visitingTeamId;

    @Field(name = "fecha")
    private LocalDateTime dateTime;

    @Field(name = "lugar")
    private String location;

    @Field(name = "estadio")
    private String stadium;

    @Field(name = "estadistica_id")
    private String statId;

}
