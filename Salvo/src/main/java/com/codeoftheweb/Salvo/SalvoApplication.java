package com.codeoftheweb.Salvo;

import com.codeoftheweb.Salvo.models.*;
import com.codeoftheweb.Salvo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
		System.out.println("Welcome to Salvo");
	}
	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository, SalvoRepository salvoRepository, ScoreRepository scoreRepository){
		return (args) -> {

			Player player1 = new Player("fede16", "j.bauer@ctu.gov", passwordEncoder.encode("1234"));
			Player player2 = new Player("clara15", "c.obrian@ctu.gov", passwordEncoder.encode("12345"));
			Player player3 = new Player("marco17", "t.almeida@ctu.gov", passwordEncoder.encode("123456"));
			Player player4 = new Player("lucia22", "t.lucia@ctu.gov", passwordEncoder.encode("1234567"));

			playerRepository.saveAll(List.of(player1, player2, player3, player4));

			/*
			Game game1 = new Game(LocalDateTime.now());
			Game game2 = new Game(LocalDateTime.now().plusHours(1));
			Game game3 = new Game(LocalDateTime.now().plusHours(2));
			Game game4 = new Game(LocalDateTime.now().plusHours(4));

			gameRepository.saveAll(List.of(game1, game2, game3, game4));

			GamePlayer gamePlayer1 = new GamePlayer(LocalDateTime.now(), player1, game1);
			GamePlayer gamePlayer2 = new GamePlayer(LocalDateTime.now(), player2, game1);
			GamePlayer gamePlayer3 = new GamePlayer(LocalDateTime.now(), player3, game2);
			GamePlayer gamePlayer4 = new GamePlayer(LocalDateTime.now(), player4, game2);
			GamePlayer gamePlayer5 = new GamePlayer(LocalDateTime.now(), player1, game3);
			GamePlayer gamePlayer6 = new GamePlayer(LocalDateTime.now(), player4, game3);
			GamePlayer gamePlayer7 = new GamePlayer(LocalDateTime.now(), player4, game4);

			gamePlayerRepository.saveAll(List.of(gamePlayer1, gamePlayer2, gamePlayer3, gamePlayer4, gamePlayer5, gamePlayer6, gamePlayer7));

			Ship ship1 = new Ship(gamePlayer1, TypeShip.DESTROYER, List.of("H2", "H3", "H4", "H5"));
			Ship ship2 = new Ship(gamePlayer1, TypeShip.CARRIER, List.of("D10", "C10"));
			Ship ship3 = new Ship(gamePlayer1, TypeShip.CARRIER, List.of("A1", "A2", "A3"));
			Ship ship4 = new Ship(gamePlayer1, TypeShip.CARRIER, List.of("H7", "I7", "J7"));
			Ship ship5 = new Ship(gamePlayer2, TypeShip.SUBMARINE, List.of("G4", "G5", "G6", "G7", "G8"));
			Ship ship6 = new Ship(gamePlayer2, TypeShip.BATTLESHIP, List.of("B2", "C2"));
			Ship ship7 = new Ship(gamePlayer5, TypeShip.DESTROYER, List.of("I2", "I3", "I4", "I5"));
			Ship ship8 = new Ship(gamePlayer5, TypeShip.CARRIER, List.of("D10", "C10"));
			Ship ship9 = new Ship(gamePlayer5, TypeShip.CARRIER, List.of("A1", "A2", "A3"));
			Ship ship10 = new Ship(gamePlayer5, TypeShip.CARRIER, List.of("H7", "I7", "J7"));
			Ship ship11 = new Ship(gamePlayer6, TypeShip.SUBMARINE, List.of("G4", "G5", "G6", "G7", "G8"));
			Ship ship12 = new Ship(gamePlayer6, TypeShip.BATTLESHIP, List.of("B2", "C2"));

			shipRepository.saveAll(List.of(ship1, ship2, ship3, ship4, ship5, ship6, ship7, ship8, ship9, ship10, ship11, ship12));

			Salvo salvo1 = new Salvo(1, gamePlayer1, List.of("SH3", "SB4"));
			Salvo salvo2 = new Salvo(1, gamePlayer2, List.of("SB5", "SC1"));
			Salvo salvo3 = new Salvo(2, gamePlayer1, List.of("SI6", "SJ9"));
			Salvo salvo4 = new Salvo(2, gamePlayer2, List.of("SA1", "SF10"));
			Salvo salvo5 = new Salvo(1, gamePlayer5, List.of("SH3", "SB4"));
			Salvo salvo6 = new Salvo(1, gamePlayer6, List.of("SB9", "SC2"));
			Salvo salvo7 = new Salvo(2, gamePlayer5, List.of("SI6", "SJ9"));
			Salvo salvo8 = new Salvo(2, gamePlayer6, List.of("SA4", "SF5"));

			salvoRepository.saveAll(List.of(salvo1, salvo2, salvo3, salvo4, salvo5, salvo6, salvo7, salvo8));

			Score score1 = new Score(LocalDateTime.now(), 1, player1, game1);
			Score score2 = new Score(LocalDateTime.now(), 0.5, player1, game2);
			Score score3 = new Score(LocalDateTime.now(), 0, player2, game1);
			Score score4 = new Score(LocalDateTime.now(), 0.5, player2, game2);
			Score score5 = new Score(LocalDateTime.now(), 1, player3, game3);

			scoreRepository.saveAll(List.of(score1, score2, score3, score4, score5));*/

		};
	}

}
