package com.codeoftheweb.Salvo.service;

import com.codeoftheweb.Salvo.dto.GameDTO;
import com.codeoftheweb.Salvo.dto.GamePlayerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface GameService {

    GameDTO getGame(Long id);

    GamePlayerDTO getPlayerView(Long gp);

    List<GamePlayerDTO> getGames();

    ResponseEntity<?> createGame(Authentication authentication);

    ResponseEntity<?> joinGame(Long idGame, Authentication authentication);
}
