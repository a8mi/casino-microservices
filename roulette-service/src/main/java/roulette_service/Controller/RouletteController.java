package roulette_service.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import roulette_service.Handler.IRouletteHandler;
import roulette_service.Requests.RouletteGameStartRequest;
import roulette_service.View.IRouletteGameView;

@RestController
public class RouletteController implements IRouletteController {
    
    private final IRouletteHandler rouletteHandler;

    public RouletteController(IRouletteHandler rouletteHandler){
        
        this.rouletteHandler = rouletteHandler;
    }

    @Override
    public ResponseEntity<IRouletteGameView> playGame(RouletteGameStartRequest gameStartRequest) {
        
        var result = rouletteHandler.createGame(gameStartRequest);

        if(result.isPresent()) return ResponseEntity.ok(result.get());

        return ResponseEntity.internalServerError().build();
    }
}
