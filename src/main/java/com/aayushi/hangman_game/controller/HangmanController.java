package com.aayushi.hangman_game.controller;

import com.aayushi.hangman_game.model.GameState;
import com.aayushi.hangman_game.service.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
public class HangmanController {

    private final GameService service;

    public HangmanController(GameService service) {
        this.service = service;
    }
    @PostMapping("/start")
    public GameState start(@RequestParam String category,
                           @RequestParam String difficulty) {
        return service.startGame(category, difficulty);
    }
    @PostMapping("/guess/{letter}")
    public GameState guess(@PathVariable char letter) {
        return service.guess(letter);
    }
}
