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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStats'");
    }

    @Override
    public ResponseEntity<IRouletteUserStatsView> getUserStatsById(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserStatsById'");
    }

    @Override
    public ResponseEntity<List<IRouletteGameView>> getAllRouletteGames() {
        var result = rouletteHandler.getAllRouletteGames();
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<IRouletteGameView> getGameById(Long gameId) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getGameById'");
    }

    @Override
    public ResponseEntity<IRouletteGameView> deleteGameById(Long gameId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteGameById'");
    }
}
