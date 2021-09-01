package com.codeoftheweb.Salvo.service.implement;

import com.codeoftheweb.Salvo.dto.PLayerDTO;
import com.codeoftheweb.Salvo.models.Player;
import com.codeoftheweb.Salvo.repositories.PlayerRepository;
import com.codeoftheweb.Salvo.service.PLayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerImplement implements PLayerService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public PLayerDTO getPlayer(Authentication authentication) {
        Player player = playerRepository.findByUser(authentication.getName());
        return new PLayerDTO(player);
    }

    @Override
    public List<PLayerDTO> getPlayers() {
        return playerRepository.findAll().stream().map(PLayerDTO::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> createPlayer(String user, String email, String password) {
        Player player = playerRepository.findByEmail(email);
        Player player1 = playerRepository.findByUser(user);

        if (user.isEmpty() || email.isEmpty() || password.isEmpty()){
            return new ResponseEntity<>("Empty", HttpStatus.FORBIDDEN);
        }
        if (player != null || player1 != null){
            return new ResponseEntity<>("email or user in use", HttpStatus.FORBIDDEN);
        }
        else {
            Player playerNew = new Player(user, email, passwordEncoder.encode(password));
            playerRepository.save(playerNew);
            return new ResponseEntity<>("register complete", HttpStatus.CREATED);
        }
    }
}
