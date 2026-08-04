package roulette_service.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import roulette_service.Requests.RouletteGameStartRequest;
import roulette_service.View.IRouletteGameView;
import roulette_service.View.IRoulettePlayGameView;
import roulette_service.View.IRouletteStatsView;
import roulette_service.View.IRouletteUserStatsView;

@RequestMapping("casino/roulette/api")
public interface IRouletteController {
    
    @PostMapping("/play")
    ResponseEntity<IRoulettePlayGameView> playGame( @RequestBody RouletteGameStartRequest gameStartRequest );
    
    @GetMapping("/info/rules")
    ResponseEntity<String> getRules();

    @GetMapping("/info/chances")
    ResponseEntity<String> getChances();

    @GetMapping("/stats")
    ResponseEntity<IRouletteStatsView> getStats();

    @GetMapping("/stats/user/{userId}")
    ResponseEntity<IRouletteUserStatsView> getUserStatsById(@PathVariable Long userId);

    @GetMapping("/stats/games")
    ResponseEntity<List<IRouletteGameView>> getAllRouletteGames();

    @GetMapping("/stat/{gameId}")
    ResponseEntity<IRouletteGameView> getGameById(@PathVariable Long gameId); 

    @DeleteMapping("/stat/{gameId}")
    ResponseEntity<IRouletteGameView> deleteGameById(@PathVariable Long gameId);

}