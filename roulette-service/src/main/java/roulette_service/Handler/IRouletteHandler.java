package roulette_service.Handler;

import java.util.List;
import java.util.Optional;

import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.View.IRouletteGameView;
import roulette_service.View.IRoulettePlayGameView;
import roulette_service.View.IRouletteStatsView;
import roulette_service.View.IRouletteUserStatsView;

public interface IRouletteHandler {
   
    Optional<IRoulettePlayGameView> playGame(IRouletteGameStartRequest rouletteGameStartRequest);
    
    List<IRouletteGameView> getAllGames();
    
    IRouletteGameView getGameById(Long id);
    
    IRouletteGameView deleteGame(Long id);
    
    IRouletteStatsView getStats();
    
    IRouletteUserStatsView getStatsById(Long id);
    
    String getRules();
    
    String getChances();
}
