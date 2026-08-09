package roulette_service.Gamelogic;

import roulette_service.Requests.IRouletteGameStartRequest;

public class RouletteGameLogicFactory implements IRouletteGameLogicFactory {

    @Override
    public IRouletteGameLogic create(ERouletteGameType eRouletteGameType, IRouletteGameStartRequest rouletteGameStartRequest, int ballPosition) {
        
        if (eRouletteGameType == ERouletteGameType.COLUMN) return ColumnGame.create(rouletteGameStartRequest, ballPosition);
        if (eRouletteGameType == ERouletteGameType.DOZEN) return DozenGame.create(rouletteGameStartRequest, ballPosition);
        if (eRouletteGameType == ERouletteGameType.SINGLE) return SingleGame.create(rouletteGameStartRequest, ballPosition);
        if (eRouletteGameType == ERouletteGameType.SPLIT) return SplitGame.create(rouletteGameStartRequest, ballPosition);
        if (eRouletteGameType == ERouletteGameType.CORNER) return CornerGame.create(rouletteGameStartRequest, ballPosition);
        if (eRouletteGameType == ERouletteGameType.SIXLINE) return SixLineGame.create(rouletteGameStartRequest, ballPosition);
        if (eRouletteGameType == ERouletteGameType.STREET) return StreetGame.create(rouletteGameStartRequest, ballPosition);
        if (eRouletteGameType == ERouletteGameType.RED) return RedGame.create(rouletteGameStartRequest, ballPosition);
        if (eRouletteGameType == ERouletteGameType.BLACK) return BlackGame.create(rouletteGameStartRequest, ballPosition);
        if (eRouletteGameType == ERouletteGameType.EVEN) return EvenGame.create(rouletteGameStartRequest, ballPosition);
        if (eRouletteGameType == ERouletteGameType.ODD) return OddGame.create(rouletteGameStartRequest, ballPosition);
        if (eRouletteGameType == ERouletteGameType.HIGH) return HighGame.create(rouletteGameStartRequest, ballPosition);
        if (eRouletteGameType == ERouletteGameType.LOW) return LowGame.create(rouletteGameStartRequest, ballPosition);
        
        return null;
    }
    
}
