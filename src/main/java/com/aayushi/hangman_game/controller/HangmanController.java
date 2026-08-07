package com.aayushi.hangman_game.controller;
import jakarta.servlet.http.HttpSession;
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
                           @RequestParam String difficulty, 
                            HttpSession session){
        return service.startGame(session.getId(), category, difficulty);
    }
    @PostMapping("/guess/{letter}")
    public GameState guess(@PathVariable char letter, HttpSession session) {
        return service.guess(session.getId(), letter);
    }
}
