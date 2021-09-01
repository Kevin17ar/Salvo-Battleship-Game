package com.codeoftheweb.Salvo.dto;

import com.codeoftheweb.Salvo.models.Ship;
import com.codeoftheweb.Salvo.models.TypeShip;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ShipDTO {
    private TypeShip type;
    private List<String> locations;

    public ShipDTO(Ship ship){
        this.type = ship.getTypeShip();
        this.locations = ship.getLocations();
    }
}
