package com.codeoftheweb.Salvo.service;

import com.codeoftheweb.Salvo.dto.PLayerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface PLayerService {
    PLayerDTO getPlayer(Authentication authentication);

    List<PLayerDTO> getPlayers();

    ResponseEntity<?> createPlayer(String user, String email, String password);
}
