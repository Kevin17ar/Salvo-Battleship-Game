package com.codeoftheweb.Salvo.models;

import com.codeoftheweb.Salvo.repositories.ScoreRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@NoArgsConstructor
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private LocalDateTime createGamePlayer;

    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_player")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_game")
    private Game game;

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Ship> ships = new HashSet<>();

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    private Set<Salvo> salvos = new HashSet<>();

    public GamePlayer(LocalDateTime createGamePlayer ,Player player, Game game) {
        this.createGamePlayer = createGamePlayer;
        this.player = player;
        this.game = game;
    }

    public List<Player> getPlayers(){
        return this.game.getPlayers();
    }

    public Score getScore(){
        return player.getScore(game);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreateGamePlayer() {
        return createGamePlayer;
    }

    public void setCreateGamePlayer(LocalDateTime createGamePlayer) {
        this.createGamePlayer = createGamePlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    public Set<Salvo> getSalvos() {
        return salvos;
    }

    public void setSalvos(Set<Salvo> salvos) {
        this.salvos = salvos;
    }

    public List<Ship> getSinks() {//metodo para obtener ships hundidos.
        List<String> mySalvos = new ArrayList<>(); //creamos una lista vacia de positions.

        for (Salvo salvo: this.salvos) {//añadimos todas las positions nuestas en una sola lista.
            mySalvos.addAll(salvo.getLocations());
        }
        GamePlayer gamePlayer = this.game.getGamePlayers().stream().filter(pl -> pl.getId() != this.id).findFirst().orElse(null); //obtenemos el gameplayer enemy.

        List<Ship> sinksS = new ArrayList<>();//creamos una lista vacia donde guardaremos los ships del enemy hundidos.

        if (gamePlayer != null){
            Set<Ship> ships = gamePlayer.getShips();//obtenemos los ships del enemy.
            for (Ship ship: ships) {//recorremos cada ship del enemy.
                int contandor = 0;
                for (String position: mySalvos) {//recorremos nuestra lista mysalvos donde se encuentra nuestras dispararos "A1".
                    if (ship.getLocations().contains(position)){//preguntamos si cada disparo conoincdide con las posciones de los ships del enemy.
                        contandor += 1;
                    }
                }
                if (ship.getLocations().toArray().length == contandor) {//preguntamos, si el ship que recorremos de ships es igual al contador.
                    sinksS.add(ship);//si es true, añade justo ese ship especifco a la lista de hundidos.
                }
            }
            return sinksS;//retornamos la lista con los hundidos ya sea vacio o no.
        }
        return sinksS;// retornamos una lista vacia en caso de que el player 2 no se halla unido al game.
    }

    public int getMyShipsSinks(){//metodo para devolver el numero de ships que tengo a flote.
        GamePlayer gamePlayerEnemy = this.game.getGamePlayers().stream().filter(pl -> pl.getId() != this.id).findFirst().orElse(null); //obtenemos el gameplayer enemy.
        if (gamePlayerEnemy != null){
            int sinks = gamePlayerEnemy.getSinks().size();
            return this.ships.size() - sinks;
        }
        return 0;
    }

    public String getStatus(){
        GamePlayer gamePlayerEnemy = this.game.getGamePlayers().stream().filter(pl -> pl.getId() != this.id).findFirst().orElse(null);

        if (gamePlayerEnemy != null){
            Set<Salvo> salvosPlayer1 = this.getSalvos();
            if (salvosPlayer1.toArray().length > gamePlayerEnemy.getSalvos().size()) {
                return "wait_salvos_enemy";
            }
            if (this.getSinks().size() == 5 && this.getMyShipsSinks() == 0 && salvosPlayer1.size() == gamePlayerEnemy.getSalvos().size()){
                //scoreRepository.save(new Score(LocalDateTime.now(), 0.5, this.player, this.game));
                return "draw";
            }
            if (this.getMyShipsSinks() == 0 && salvosPlayer1.size() == gamePlayerEnemy.getSalvos().size() && salvosPlayer1.size() > 1){
                //scoreRepository.save(new Score(LocalDateTime.now(), 0, this.player, this.game));
                return "loser";
            }
            if (getSinks().size() == 5 && gamePlayerEnemy.getSalvos().size() == salvosPlayer1.size()){
                //scoreRepository.save(new Score(LocalDateTime.now(), 1, this.player, this.game));
                return "winner";
            }
            if(gamePlayerEnemy.getSalvos().toArray().length == salvosPlayer1.size()){//comparo el length de los salvos de los 2 player.
                return "shoot";
            }
        }
        return "wait player 2";
    }

}
