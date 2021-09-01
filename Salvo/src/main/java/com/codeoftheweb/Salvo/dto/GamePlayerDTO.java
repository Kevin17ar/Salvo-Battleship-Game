package com.codeoftheweb.Salvo.dto;

import com.codeoftheweb.Salvo.models.*;
import com.codeoftheweb.Salvo.repositories.ScoreRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GamePlayerDTO {
    private Long id;
    private Long idGame;
    private Player player;
    private String player2;
    private String status;
    private Set<ShipDTO> ships = new HashSet<>();
    private List<SalvoDTO> salvos = new ArrayList<>();
    private List<ShipDTO> sinks = new ArrayList<>();
    private int myShipsNoSinks;



    public GamePlayerDTO(GamePlayer gamePlayer) {
        this.id = gamePlayer.getId();
        this.idGame = gamePlayer.getGame().getId();
        this.player = gamePlayer.getPlayer();
        this.player2 = getUser2(gamePlayer);
        this.status = gamePlayer.getStatus();
        this.ships = gamePlayer.getShips().stream().map(ShipDTO::new).collect(Collectors.toSet());
        this.salvos = gamePlayer.getSalvos().stream().map(SalvoDTO::new).collect(Collectors.toList());
        this.sinks = gamePlayer.getSinks().stream().map(ShipDTO::new).collect(Collectors.toList());
        this.myShipsNoSinks = gamePlayer.getMyShipsSinks();
    }

    public String getUser2(GamePlayer gamePlayer){
        long id = this.player.getId();
        Player player2= gamePlayer.getGame().getPlayers().stream().filter(player -> player.getId() != id).findFirst().orElse(null);

        if (player2 == null){
            return "Esperando jugador";
        }
        return player2.getUser();
    }

}
