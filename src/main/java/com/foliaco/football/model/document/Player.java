package com.foliaco.football.model.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "jugadores")
public class Player {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field(name = "nombre")
    private String name;

    @Field(name = "dorsal")
    private int bib;

    @Field(name = "nacionalidad")
    private String nationality;

    @Field(name = "posicion")
    private String position;

    @Field(name = "fecha_nacimiento")
    private LocalDate birthDate;

    @Field(name = "foto")
    private String photoUrl;

    @Field(name = "goles")
    private int goals;

    @Field(name = "asistencias")
    private int assists;

    @Field(name = "numero_trofeos")
    private int trophies;

}
