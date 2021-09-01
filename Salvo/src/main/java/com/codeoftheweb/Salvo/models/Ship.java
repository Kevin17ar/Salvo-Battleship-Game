package com.codeoftheweb.Salvo.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private TypeShip typeShip;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_gamePlayer")
    private GamePlayer gamePlayer;

    @ElementCollection
    private List<String> locations = new ArrayList<>();

    public Ship(GamePlayer gamePlayer,TypeShip typeShip, List<String> locations) {
        this.gamePlayer = gamePlayer;
        this.typeShip = typeShip;
        this.locations = locations;
    }

    public Player getPlayer(){
        return gamePlayer.getPlayer();
    }

}
