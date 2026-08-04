package roulette_service.Handler;

import java.util.List;
import java.util.Optional;

import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.View.IRouletteGameView;
import roulette_service.View.IRoulettePlayGameView;

public interface IRouletteHandler {
    Optional<IRoulettePlayGameView> createGame(IRouletteGameStartRequest rouletteGameStartRequest);

    List<IRouletteGameView> getAllRouletteGames();

}
