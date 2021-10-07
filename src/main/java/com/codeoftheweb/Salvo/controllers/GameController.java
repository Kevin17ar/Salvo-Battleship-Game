package com.codeoftheweb.Salvo.controllers;

import ch.qos.logback.core.joran.action.IADataForComplexProperty;
import com.codeoftheweb.Salvo.dto.*;
import com.codeoftheweb.Salvo.models.*;
import com.codeoftheweb.Salvo.repositories.*;
import com.codeoftheweb.Salvo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class GameController {
    @Autowired
    GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private SalvoRepository salvoRepository;

    @GetMapping("/games")
    public List<GamePlayerDTO> getGames(){
        return gameService.getGames();
    }

    @GetMapping("/games/{id}")
    public GameDTO getGames(@PathVariable Long id){
        return gameService.getGame(id);
    }

    @GetMapping("/games_view/{gp}")
    public GamePlayerDTO getPlayerView(@PathVariable Long gp){
        return gameService.getPlayerView(gp);
    }

    @PostMapping("/games/current/game")
    public ResponseEntity<?> createGame(Authentication authentication){
        return gameService.createGame(authentication);
    }

    private Map<String, Object> makeMap(Object value){
        Map<String, Object> map = new HashMap<>();
        map.put("gpId", value);
        return map;
    }

    @PostMapping("/games/{idGame}/players")
    public ResponseEntity<?> joinGame(@PathVariable Long idGame, Authentication authentication){
        return gameService.joinGame(idGame, authentication);
    }

    @PostMapping("/games/player/{gpId}/ships")
    public ResponseEntity<?> addShips(Authentication authentication, @PathVariable Long gpId, @RequestBody List<ShipSendDTO> ships){
        Player player = playerRepository.findByUser(authentication.getName());
        GamePlayer gamePlayer = gamePlayerRepository.findById(gpId).orElse(null);

        if (ships.isEmpty()){
            return new ResponseEntity<>("Empty", HttpStatus.FORBIDDEN);
        }
        if (authentication.getName() == null) {
            return new ResponseEntity<>("NOT AUTHENTICATION", HttpStatus.UNAUTHORIZED);
        }
        if (gamePlayer == null){
            return new ResponseEntity<>("not found gameplayer", HttpStatus.FORBIDDEN);
        }
        if (gamePlayer.getPlayer() != player){
            new ResponseEntity<>("mismo player", HttpStatus.FORBIDDEN);
        }
        if (gamePlayer.getShips().toArray().length > 0){
            return new ResponseEntity<>("have ships", HttpStatus.FORBIDDEN);
        }
        else {
            List<Ship> shipList= ships.stream().map(ship -> new Ship(gamePlayer, TypeShip.valueOf(ship.getType()), ship.getLocations())).collect(Collectors.toList());
            shipRepository.saveAll(shipList);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PostMapping("/games/players/{gpId}/salvos")
    public ResponseEntity<?> shoot(Authentication authentication, @PathVariable Long gpId,@RequestBody Salvo salvo){
        Player player = playerRepository.findByUser(authentication.getName());
        GamePlayer gamePlayer = gamePlayerRepository.findById(gpId).orElse(null);

        if (gamePlayer == null){
            return new ResponseEntity<>("Empty", HttpStatus.BAD_REQUEST);
        }
        if (player == null){
            return new ResponseEntity<>("no user", HttpStatus.UNAUTHORIZED);
        }
        if (!player.getGamePlayers().contains(gamePlayer)){
            return new ResponseEntity<>("no gp in player", HttpStatus.UNAUTHORIZED);
        }
        if (gamePlayer.getSalvos().stream().filter(e -> e.getTurn() == salvo.getTurn()).toArray().length != 0){
            return new ResponseEntity<>("turn exist", HttpStatus.FORBIDDEN);
        }
        else{
            Salvo salvo1 = new Salvo(salvo.getTurn(), gamePlayer, salvo.getLocations());
            salvoRepository.save(salvo1);

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }

    @PostMapping("/games/over/{gpId}")
    public ResponseEntity<?> home(Authentication authentication, @PathVariable Long gpId, @RequestParam String status){
        Player player = playerRepository.findByUser(authentication.getName());
        GamePlayer gamePlayer = gamePlayerRepository.findById(gpId).orElse(null);

        if (gamePlayer == null){
            return new ResponseEntity<>("Empty", HttpStatus.BAD_REQUEST);
        }
        if (player == null){
            return new ResponseEntity<>("no user", HttpStatus.UNAUTHORIZED);
        }
        if (status.isEmpty()){
            return new ResponseEntity<>("empty",HttpStatus.FORBIDDEN);
        }
        else {
            Game game = gamePlayer.getGame();
            if (status.equals("winner")) {
                Score score = new Score(LocalDateTime.now(), 1, player, game);
                scoreRepository.save(score);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
            if (status.equals("loser")) {
                scoreRepository.save(new Score(LocalDateTime.now(), 0, player, game));
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
            if (status.equals("draw")) {
                scoreRepository.save(new Score(LocalDateTime.now(), 0.5, player, game));
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
    }
}
