package com.codeoftheweb.Salvo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String user;
    private String email;
    private String password;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<GamePlayer> gamePlayers = new HashSet<>();

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private Set<Score> scores = new HashSet<>();

    public Player(String user, String email, String password) {
        this.user = user;
        this.email = email;
        this.password = password;
    }

    public Score getScore(Game game){
        return this.scores.stream().filter(score -> score.getGame() == game).findFirst().orElse(null);
    }

    /*public List<Set<Salvo>> getSalvos(){
        return this.gamePlayers.stream().map(GamePlayer::getSalvos).collect(Collectors.toList());
    }*/

    @JsonIgnore
    public List<Game> getGames(){
        return gamePlayers.stream().map(GamePlayer::getGame).collect(Collectors.toList());
    }

    @JsonIgnore
    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }
}
