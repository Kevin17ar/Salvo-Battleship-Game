package com.codeoftheweb.Salvo.dto;

import com.codeoftheweb.Salvo.models.Score;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ScoreDTO {
    private Long id;
    private Long gameId;
    private Long playerId;
    private double score;
    private LocalDateTime finishDate;

    public ScoreDTO(Score score) {
        this.id = score.getId();
        this.gameId = score.getGame().getId();
        this.playerId = score.getPlayer().getId();
        this.score = score.getScore();
        this.finishDate = score.getFinishDate();
    }
}
