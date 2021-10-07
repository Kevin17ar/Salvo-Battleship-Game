package com.codeoftheweb.Salvo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private LocalDateTime finishDate;

    private double score;

    @ManyToOne(fetch = FetchType.EAGER)
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    private Game game;

    public Score(LocalDateTime finishDate, double score, Player player, Game game) {
        this.finishDate = finishDate;
        this.score = score;
        this.player = player;
        this.game = game;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @JsonIgnore
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @JsonIgnore
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
