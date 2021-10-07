package com.codeoftheweb.Salvo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ShipSendDTO {

    private String type;
    private List<String> locations;

    public ShipSendDTO(String type, List<String> locations) {
        this.type = type;
        this.locations = locations;
    }
}
