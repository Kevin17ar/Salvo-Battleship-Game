package com.codeoftheweb.Salvo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private LocalDateTime createGame;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayers = new HashSet<>();

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private Set<Score> scores = new HashSet<>();

    public Game(LocalDateTime createGame) {
        this.createGame = createGame;
    }

    @JsonIgnore
    public List<Player> getPlayers(){
        return gamePlayers.stream().map(GamePlayer::getPlayer).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreateGame() {
        return createGame;
    }

    public void setCreateGame(LocalDateTime createGame) {
        this.createGame = createGame;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }
}
