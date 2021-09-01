package com.codeoftheweb.Salvo.controllers;

import com.codeoftheweb.Salvo.dto.PLayerDTO;
import com.codeoftheweb.Salvo.models.Player;
import com.codeoftheweb.Salvo.repositories.PlayerRepository;
import com.codeoftheweb.Salvo.service.PLayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PlayerController {

    @Autowired
    PLayerService pLayerService;

    @GetMapping("/player/current")
    public PLayerDTO getPlayer(Authentication authentication){
        return pLayerService.getPlayer(authentication);
    }

    @GetMapping("/games/players")
    public List<PLayerDTO> getPlayers(){
        return  pLayerService.getPlayers();
    }

    @PostMapping("/players")
    public ResponseEntity<?> createPlayer(@RequestParam String user, @RequestParam String email, @RequestParam String password){
        return pLayerService.createPlayer(user, email, password);
    }
}
