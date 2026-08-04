package roulette_service.Handler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import roulette_service.Gamelogic.ERouletteGameType;
import roulette_service.Gamelogic.IRouletteGameLogic;

import roulette_service.Gamelogic.RouletteGameLogicFactory;
import roulette_service.Model.IRouletteGameFactory;
import roulette_service.Model.RouletteGame;
import roulette_service.Model.IRouletteGame;
import roulette_service.Repository.IRouletteGameRepository;
import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.Utils.RouletteGameValidation;
import roulette_service.View.IRouletteGameView;
import roulette_service.View.IRoulettePlayGameView;
import roulette_service.View.RouletteGameView;
import roulette_service.View.RoulettePlayGameView;

public class RouletteHandler implements IRouletteHandler {
    private IRouletteGameFactory factory;
    private IRouletteGameRepository repository;

    public RouletteHandler( IRouletteGameRepository repository, IRouletteGameFactory rouletteGameFactory){
        this.repository = repository;
        this.factory = rouletteGameFactory;
    }

    @Override
    public List<IRouletteGameView> getAllRouletteGames() {
        return repository.findAll()
                .stream()
                .map(RouletteGameView::of)
                .toList();
    }

    @Override
    public Optional<IRoulettePlayGameView> createGame(IRouletteGameStartRequest rouletteGameStartRequest) {

        String betType = rouletteGameStartRequest.getBetType();
        int[] nums = rouletteGameStartRequest.getBet();
        RouletteGameLogicFactory rouletteGameLogicFactory = new RouletteGameLogicFactory();
    
        if (!RouletteGameValidation.validNums(nums, 0, 36) || 
            RouletteGameValidation.hasDuplicates(nums)) 
            return Optional.empty();

        IRouletteGameLogic rouletteGameLogic = rouletteGameLogicFactory.create(ERouletteGameType.valueOf(betType.toUpperCase()), rouletteGameStartRequest);
        
        if (rouletteGameLogic == null){
            return Optional.empty();
        }
        
        rouletteGameLogic.playGame();

        System.out.println(rouletteGameLogic.getBet().toString());
        System.out.println(rouletteGameLogic.getResult());
        System.out.println(rouletteGameLogic.getIsWin());
        System.out.println(rouletteGameLogic.getPayout());
        

        RoulettePlayGameView rouletteGameView = new RoulettePlayGameView(
            rouletteGameStartRequest.getUserId(),
            betType,
            rouletteGameLogic.getBet(),
            rouletteGameStartRequest.getAmount(),
            rouletteGameLogic.getResult(),
            rouletteGameLogic.getIsWin(),
            rouletteGameLogic.getPayout()
        );

        IRouletteGame rouletteGameModel = 
        factory.create(rouletteGameStartRequest.getUserId(),
                        BigDecimal.valueOf(rouletteGameStartRequest.getAmount()),
                        rouletteGameLogic.getIsWin());

        repository.save((RouletteGame) rouletteGameModel);

        return Optional.of(rouletteGameView);
        }

    }
