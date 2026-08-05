package roulette_service.Gamelogic;

import roulette_service.Requests.IRouletteGameStartRequest;

public interface IRouletteGameLogicFactory {

    IRouletteGameLogic create(ERouletteGameType eRouletteGameType, IRouletteGameStartRequest rouletteGameStartRequest);

}
