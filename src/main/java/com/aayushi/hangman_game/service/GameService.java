package com.aayushi.hangman_game.service;

import com.aayushi.hangman_game.model.GameState;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService{
    private Map<String, GameState> games=new ConcurrentHashMap<>();
    private Map<String, String[]> categories = Map.of(
        "Animals", new String[]{"elephant", "tiger", "blue whale", "dolphin", "coyote", "penguin", "chimpanzee", "squirrel",
        "kangaroo", "panda", "orangutan", "hedgehog", "chameleon", "cheetah", "leopard", "woodpecker", "crocodile", "hippopotamus", 
        "beluga whale", "bat"
        },
        "Movies", new String[]{"iron man", "jurassic park", "harry potter", "oppenheimer", "hunger games", "cruella", "maze runner",
        "avengers", "dead poets society", "mrs doubtfire", "legally blonde", "easy a", "zootopia", "drishyam", "sooryavansham", "chupke chupke",
        "five feet apart", "the great flood", "mission impossible", "top gun"
        },
        "Countries", new String[]{"india", "canada", "france", "australia", "germany", "south korea","greenland", "united states of america", "philippines",
        "thailand", "united kingdom", "ukraine", "czech republic", "netherlands", "switzerland", "croatia", "lithuania", "luxembourg", "monaco", "afghanistan"
        }
    );
    public GameState startGame(String sessionId, String category, String difficulty){
        GameState game = new GameState();
        String[] words = categories.get(category);
        game.word = words[new Random().nextInt(words.length)];
        game.maxAttempts = difficulty.equals("Hard") ? 5 :
                           difficulty.equals("Medium") ? 7 : 10;
        game.attempts = game.maxAttempts;
        game.score = 0;
        games.put(sessionId, game);
        return game;
    }
    public GameState guess(String sessionId, char letter){
        GameState game=games.get(sessionId);
        if (game==null){
            throw new IllegalStateException("No active game-call /start first");
        }
        game.guessed.add(letter);
        if (!game.word.contains(String.valueOf(letter))) {
            game.attempts--;
        } else {
            game.score += 10;
        }
        return game;
    }
}
