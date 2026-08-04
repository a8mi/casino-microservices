package roulette_service.Gamelogic;

import roulette_service.Requests.IRouletteGameStartRequest;

public class RouletteGameLogicFactory implements IRouletteGameLogicFactory {

    @Override
    public IRouletteGameLogic create(ERouletteGameType eRouletteGameType, IRouletteGameStartRequest rouletteGameStartRequest) {
        
        if (eRouletteGameType == ERouletteGameType.SINGLE) return SingleGame.create(rouletteGameStartRequest);
        if (eRouletteGameType == ERouletteGameType.SPLIT) return SplitGame.create(rouletteGameStartRequest);
        if (eRouletteGameType == ERouletteGameType.CORNER) return CornerGame.create(rouletteGameStartRequest);
        if (eRouletteGameType == ERouletteGameType.SIXLINE) return SixLineGame.create(rouletteGameStartRequest);
        if (eRouletteGameType == ERouletteGameType.STREET) return StreetGame.create(rouletteGameStartRequest);
        if (eRouletteGameType == ERouletteGameType.RED) return RedGame.create(rouletteGameStartRequest);
        if (eRouletteGameType == ERouletteGameType.BLACK) return BlackGame.create(rouletteGameStartRequest);
        if (eRouletteGameType == ERouletteGameType.EVEN) return EvenGame.create(rouletteGameStartRequest);
        if (eRouletteGameType == ERouletteGameType.ODD) return OddGame.create(rouletteGameStartRequest);
        if (eRouletteGameType == ERouletteGameType.HIGH) return HighGame.create(rouletteGameStartRequest);
        if (eRouletteGameType == ERouletteGameType.LOW) return LowGame.create(rouletteGameStartRequest);
        
        return null;
    }
    
}
