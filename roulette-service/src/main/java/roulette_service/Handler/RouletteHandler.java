package roulette_service.Handler;

import java.util.Optional;

import roulette_service.Gamelogic.ERouletteGameType;
import roulette_service.Gamelogic.IRouletteGame;
import roulette_service.Gamelogic.RouletteGameFactory;
import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.Utils.RouletteValidation;
import roulette_service.View.IRouletteGameView;
import roulette_service.View.RouletteGameView;

public class RouletteHandler implements IRouletteHandler {

    @Override
    public Optional<IRouletteGameView> createGame(IRouletteGameStartRequest rouletteGameStartRequest) {

        String betType = rouletteGameStartRequest.getBetType();
        int[] nums = rouletteGameStartRequest.getBet();
        RouletteGameFactory rouletteGameFactory = new RouletteGameFactory();
    
        if (!RouletteValidation.validNums(nums, 0, 36) || 
            RouletteValidation.hasDuplicates(nums)) 
            return Optional.empty();

        IRouletteGame rouletteGame = rouletteGameFactory.create(ERouletteGameType.valueOf(betType.toUpperCase()), rouletteGameStartRequest);
        
        if (rouletteGame == null){
            return Optional.empty();
        }
        
        rouletteGame.playGame();

        System.out.println(rouletteGame.getBet().toString());
        System.out.println(rouletteGame.getResult());
        System.out.println(rouletteGame.getIsWin());
        System.out.println(rouletteGame.getPayout());
        

        RouletteGameView rouletteGameView = new RouletteGameView(
        betType,
        rouletteGame.getBet(),
        rouletteGameStartRequest.getAmount(),
        rouletteGame.getResult(),
        rouletteGame.getIsWin(),
        rouletteGame.getPayout()
);
        return Optional.of(rouletteGameView);
        }

    }
