package com.codeoftheweb.Salvo.dto;

import com.codeoftheweb.Salvo.models.Game;
import com.codeoftheweb.Salvo.models.GamePlayer;
import com.codeoftheweb.Salvo.models.Player;
import com.codeoftheweb.Salvo.models.Ship;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GameDTO {
    private Long id;
    private LocalDateTime createGame;
    private Set<GamePlayerDTO> gamePlayers = new HashSet<>();
    private Set<Set<ShipDTO>> ships = new HashSet<>();
    private Set<ScoreDTO> scores = new HashSet<>();

    public GameDTO(Game game) {
        this.id = game.getId();
        this.createGame = game.getCreateGame();
        this.gamePlayers = game.getGamePlayers().stream().map(GamePlayerDTO::new).collect(Collectors.toSet());
        this.ships = game.getGamePlayers().stream().map(gpl -> gpl.getShips().stream().map(ShipDTO::new).collect(Collectors.toSet())).collect(Collectors.toSet());
        this.scores = game.getScores().stream().map(ScoreDTO::new).collect(Collectors.toSet());
    }

    /*public Set<Ship> getShips (Game game){
        return  game.getGamePlayers().stream().map((Function<GamePlayer, Object>)
    }*/
}
