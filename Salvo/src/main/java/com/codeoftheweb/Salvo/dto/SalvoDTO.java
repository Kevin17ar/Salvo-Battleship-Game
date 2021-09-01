package com.codeoftheweb.Salvo.dto;

import com.codeoftheweb.Salvo.models.Salvo;
import com.codeoftheweb.Salvo.models.Ship;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SalvoDTO {
    private int turn;
    private List<String> locations;

    public SalvoDTO(Salvo salvo) {
        this.turn = salvo.getTurn();
        this.locations = salvo.getLocations();
    }
}
