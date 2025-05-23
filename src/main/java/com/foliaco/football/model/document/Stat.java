package com.foliaco.football.model.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "estadisticas")
public class Stat {

    @Id
    private String id;

    @Field(name = "mvp")
    private String mvp;

    @Field(name = "tiros_puerta_equipo_local")
    private int shotsOnTargetLocalTeam;

    @Field(name = "tiros_puerta_equipo_visitante")
    private int shotsOnTargetVisitingTeam;

    @Field(name = "tiros_equipo_local")
    private int totalShotsLocalTeam;

    @Field(name = "tiros_equipo_visitante")
    private int totalShotsVisitingTeam;

    @Field(name = "goles_equipo_local")
    private int localTeamGoals;

    @Field(name = "goles_equipo_visitante")
    private int visitingTeamGoals;

    @Field(name = "pases_completos_equipo_local")
    private int completedPassesLocalTeam;

    @Field(name = "pases_completos_equipo_visitante")
    private int completedPassesVisitingTeam;

    @Field(name = "pases_totales_equipo_local")
    private int totalPassesLocalTeam;

    @Field(name = "pases_totales_equipo_visitante")
    private int totalPassesVisitingTeam;

    @Field(name = "faltas_equipo_local")
    private int localTeamFouls;

    @Field(name = "faltas_equipo_visitante")
    private int visitingTeamFouls;

    @Field(name = "tarjetas_amarillas_equipo_local")
    private int localTeamYellowCards;

    @Field(name = "tarjetas_amarillas_equipo_visitante")
    private int visitingTeamYellowCards;

    @Field(name = "tarjetas_rojas_equipo_local")
    private int localTeamRedCards;

    @Field(name = "tarjetas_rojas_equipo_visitante")
    private int visitingTeamRedCards;

    @Field(name = "fuera_de_juego_equipo_local")
    private int localTeamOffsides;

    @Field(name = "fuera_de_juego_equipo_visitante")
    private int visitingTeamOffsides;

    @Field(name = "corners_equipo_local")
    private int localTeamCorners;

    @Field(name = "corners_equipo_visitante")
    private int visitingTeamCorners;

    @Field(name = "posesion_equipo_local")
    private int localTeamPossession;

    @Field(name = "posesion_equipo_visitante")
    private int visitingTeamPossession;

}
