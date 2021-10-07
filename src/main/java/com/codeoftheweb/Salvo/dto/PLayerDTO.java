package com.codeoftheweb.Salvo.dto;

import com.codeoftheweb.Salvo.models.Player;
import com.codeoftheweb.Salvo.models.Score;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PLayerDTO {
    private Long id;
    private String user;
    private double total;
    private int won;
    private int lost;
    private int tied;

    public PLayerDTO(Player player) {
        this.id = player.getId();
        this.user = player.getUser();
        this.total = totalScore(player);
        this.won = won(player);
        this.lost = lost(player);
        this.tied = tied(player);
    }

    public double totalScore(Player player){
        Set<Score> scores = player.getScores();
        double total = 0;
        for (Score score :scores) {
             total += score.getScore();
        }
        return total;
    }
    public int won(Player player){
        Set<Score> scores = player.getScores();
        return scores.stream().filter(score -> score.getScore() == 1).toArray().length;
    }
    public int lost(Player player){
        Set<Score> scores = player.getScores();
        return scores.stream().filter(score -> score.getScore() == 0).toArray().length;
    }
    public int tied(Player player){
        Set<Score> scores = player.getScores();
        return scores.stream().filter(score -> score.getScore() == 0.5).toArray().length;
    }

}
