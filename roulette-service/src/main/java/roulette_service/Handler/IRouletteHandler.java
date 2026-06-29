package roulette_service.Handler;

import java.util.Optional;

import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.View.IRouletteGameView;

public interface IRouletteHandler {
    Optional<IRouletteGameView> createGame(IRouletteGameStartRequest rouletteGameStartRequest);
}
