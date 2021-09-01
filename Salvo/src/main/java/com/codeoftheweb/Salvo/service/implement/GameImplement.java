package com.codeoftheweb.Salvo.service.implement;

import com.codeoftheweb.Salvo.dto.GameDTO;
import com.codeoftheweb.Salvo.dto.GamePlayerDTO;
import com.codeoftheweb.Salvo.models.Game;
import com.codeoftheweb.Salvo.models.GamePlayer;
import com.codeoftheweb.Salvo.models.Player;
import com.codeoftheweb.Salvo.repositories.GamePlayerRepository;
import com.codeoftheweb.Salvo.repositories.GameRepository;
import com.codeoftheweb.Salvo.repositories.PlayerRepository;
import com.codeoftheweb.Salvo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GameImplement implements GameService {
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Autowired
    GameRepository gameRepository;

    @Override
    public GameDTO getGame(Long id) {
        return gameRepository.findById(id).map(GameDTO::new).orElse(null);
    }

    @Override
    public GamePlayerDTO getPlayerView(Long gp) {
        return gamePlayerRepository.findById(gp).map(GamePlayerDTO::new).orElse(null);
    }

    @Override
    public List<GamePlayerDTO> getGames() {
        List<GamePlayer> gamePlayers = gamePlayerRepository.findAll().stream().filter(gamePlayer -> gamePlayer.getPlayers().toArray().length == 1).collect(Collectors.toList());
        return gamePlayers.stream().map(GamePlayerDTO:: new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> createGame(Authentication authentication) {
        Player player = playerRepository.findByUser(authentication.getName());
        if (player == null){
            return new ResponseEntity<>("No author", HttpStatus.UNAUTHORIZED);
        }
        else {
            Game game = new Game(LocalDateTime.now());
            gameRepository.save(game);
            GamePlayer gamePlayer = new GamePlayer(LocalDateTime.now(), player, game);
            gamePlayerRepository.save(gamePlayer);

            return new ResponseEntity<>(makeMap(gamePlayer.getId()),HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<?> joinGame(Long idGame, Authentication authentication) {
        Player player = playerRepository.findByUser(authentication.getName());
        Game game = gameRepository.findById(idGame).orElse(null);

        if (player == null){
            return new ResponseEntity<>("no auth", HttpStatus.UNAUTHORIZED);
        }
        if ( game == null){
            return new ResponseEntity<>("No find game", HttpStatus.FORBIDDEN);
        }
        if (game.getPlayers().contains(player)){
            return new ResponseEntity<>("mismo jugador", HttpStatus.FORBIDDEN);
        }
        if (game.getPlayers().toArray().length != 1){
            return new ResponseEntity<>("there are more the two players", HttpStatus.FORBIDDEN);
        }
        else {
            GamePlayer gamePlayer = new GamePlayer(LocalDateTime.now(), player, game);
            gamePlayerRepository.save(gamePlayer);
            return new ResponseEntity<>(makeMap(gamePlayer.getId()), HttpStatus.ACCEPTED);
        }
    }

    private Map<String, Object> makeMap(Object value){
        Map<String, Object> map = new HashMap<>();
        map.put("gpId", value);
        return map;
    }
}
