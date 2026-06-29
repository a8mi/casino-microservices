package roulette_service.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import roulette_service.Requests.RouletteGameStartRequest;
import roulette_service.View.IRouletteGameView;

@RequestMapping("casino/roulette/api")
public interface IRouletteController {
    
    @PostMapping("/play")
    ResponseEntity<IRouletteGameView> playGame(@RequestBody RouletteGameStartRequest gameStartRequest );

}