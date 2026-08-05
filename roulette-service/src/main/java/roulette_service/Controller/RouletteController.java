package roulette_service.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import roulette_service.Handler.IRouletteHandler;
import roulette_service.Requests.RouletteGameStartRequest;
import roulette_service.Utils.RouletteGameConstants;
import roulette_service.View.IRouletteGameView;
import roulette_service.View.IRoulettePlayGameView;
import roulette_service.View.IRouletteStatsView;
import roulette_service.View.IRouletteUserStatsView;

@RestController
public class RouletteController implements IRouletteController {
    
    private final IRouletteHandler rouletteHandler;

    public RouletteController(IRouletteHandler rouletteHandler){
        
        this.rouletteHandler = rouletteHandler;
    }

    @Override
    public ResponseEntity<IRoulettePlayGameView> playGame(RouletteGameStartRequest gameStartRequest) {
        
        var result = rouletteHandler.createGame(gameStartRequest);

        if(result.isPresent()) return ResponseEntity.ok(result.get());

        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<String> getRules() {
        return ResponseEntity.ok(RouletteGameConstants.RULES); 
    }

    @Override
    public ResponseEntity<String> getChances() {
        return ResponseEntity.ok(RouletteGameConstants.CHANCES);
    }

    @Override
    public ResponseEntity<IRouletteStatsView> getStats() {
        var result = rouletteHandler.getStats();
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<IRouletteUserStatsView> getUserStatsById(Long userId) {
        var result = rouletteHandler.getStatsById(userId);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<IRouletteGameView>> getAllGames() {
        var result = rouletteHandler.getAllGames();
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<IRouletteGameView> getGameById(Long gameId) {
        return ResponseEntity.ok(rouletteHandler.getGameById(gameId));
    }

    @Override
    public ResponseEntity<IRouletteGameView> deleteGame(Long gameId) {
        return ResponseEntity.ok(rouletteHandler.deleteGame(gameId));
    }
}
