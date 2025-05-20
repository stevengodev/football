package com.foliaco.football.model.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "clubes")
public class Club {

    @Id
    private String id;

    @Field(name = "nombre")
    private String name;

    @Field(name = "pais")
    private String country;

    @Field(name = "liga")
    private String league;

    @Field(name = "nombre_presidente")
    private String president;

    @Field(name = "sigla")
    private String acronym;

    @Field(name = "escudo")
    private String badgeUrl;

    @Field(name = "fecha_fundacion")
    private LocalDate foundationDate;

    @Field(name = "nombre_estadio")
    private String stadiumName;

    @Field(name = "nombre_entrenador")
    private String coachName;

    @Field(name = "capacidad_estadio")
    private int stadiumCapacity;

    @Field(name = "ids_jugadores")
    private Set<String> playersId = new HashSet<>();

}
