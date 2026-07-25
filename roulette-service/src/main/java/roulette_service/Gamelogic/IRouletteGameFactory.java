package roulette_service.Gamelogic;

import roulette_service.Requests.IRouletteGameStartRequest;

public interface IRouletteGameFactory {

    IRouletteGame create(ERouletteGameType eRouletteGameType, IRouletteGameStartRequest rouletteGameStartRequest);

}
