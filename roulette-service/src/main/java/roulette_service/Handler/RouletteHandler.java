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
import roulette_service.View.IRouletteStatsView;
import roulette_service.View.IRouletteUserStatsView;
import roulette_service.View.RouletteGameView;
import roulette_service.View.RoulettePlayGameView;
import roulette_service.View.RouletteStatsView;
import roulette_service.View.RouletteUserStatsView;

public class RouletteHandler implements IRouletteHandler {
    private IRouletteGameFactory factory;
    private IRouletteGameRepository repository;

    public RouletteHandler( IRouletteGameRepository repository, IRouletteGameFactory rouletteGameFactory){
        this.repository = repository;
        this.factory = rouletteGameFactory;
    }

    @Override
    public IRouletteStatsView getStats(){
        List<RouletteGame> games = repository.findAll();

        long totalGames = games.size();

        long totalUsers = games.stream()
                .map(RouletteGame::getUserId)
                .distinct()
                .count();

        BigDecimal totalTurnover = games.stream()
                .map(RouletteGame::getWager)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCashout = games.stream()
                .map(RouletteGame::getBetReturn)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalProfit = totalTurnover.subtract(totalCashout);

        return RouletteStatsView.of(
            totalUsers,
            totalGames,
            totalProfit,
            totalCashout,
            totalTurnover
        );
    }

    @Override
    public IRouletteUserStatsView getStatsById(Long id){
        
        List<RouletteGame> games = repository.findAll().stream()
                .filter(game -> game.getUserId() == id)
                .toList();
        
        long totalGames = games.size();
        
        long totalWinnings = games.stream()
                .filter(RouletteGame::getIsWin)
                .count();
        
        long totalLosses = totalGames - totalWinnings;

        BigDecimal totalTurnoverFromClient = games.stream()
                .map(RouletteGame::getWager)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalClientProfit = games.stream()
                .map(game -> game.getBetReturn().subtract(game.getWager()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalProfitFromClient = totalClientProfit.negate();

        return RouletteUserStatsView.of(
            id,
            totalGames,
            totalWinnings,
            totalLosses,
            totalClientProfit,
            totalTurnoverFromClient,
            totalProfitFromClient
        );
    }

    @Override
    public List<IRouletteGameView> getAllGames() {
        return repository.findAll()
                .stream()
                .map(RouletteGameView::of)
                .toList();
    }

    @Override
    public IRouletteGameView getGameById(Long id) {
        RouletteGame game = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found"));
        return RouletteGameView.of(game);
    }

    @Override
    public IRouletteGameView deleteGame(Long id){
        RouletteGame game = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found"));
        repository.deleteById(id);
        return RouletteGameView.of(game);
    }

    @Override
    public Optional<IRoulettePlayGameView> createGame(IRouletteGameStartRequest rouletteGameStartRequest) {

        int[] nums = rouletteGameStartRequest.getBet();
        

        if (!RouletteGameValidation.validNums(nums, 0, 36) || 
            RouletteGameValidation.hasDuplicates(nums)) 
            return Optional.empty();

        
        RouletteGameLogicFactory rouletteGameLogicFactory = new RouletteGameLogicFactory();
        String betTypeStr = rouletteGameStartRequest.getBetType();
        ERouletteGameType eBetType =  ERouletteGameType.valueOf(betTypeStr.toUpperCase());

        IRouletteGameLogic rouletteGameLogic = rouletteGameLogicFactory.create(eBetType, rouletteGameStartRequest);
        
        if (rouletteGameLogic == null){
            return Optional.empty();
        }
        
        rouletteGameLogic.playGame();

        RoulettePlayGameView rouletteGameView = new RoulettePlayGameView(
            rouletteGameStartRequest.getUserId(),
            eBetType,
            rouletteGameLogic.getBet(),
            rouletteGameStartRequest.getWager(),
            rouletteGameLogic.getResult(),
            rouletteGameLogic.getIsWin(),
            rouletteGameLogic.getBetReturn()
        );

        IRouletteGame rouletteGameModel = 
        factory.create( rouletteGameStartRequest.getUserId(),
                        eBetType,
                        rouletteGameStartRequest.getBet(),
                        BigDecimal.valueOf(rouletteGameStartRequest.getWager()),
                        rouletteGameLogic.getResult(),
                        rouletteGameLogic.getIsWin(),
                        BigDecimal.valueOf(rouletteGameLogic.getBetReturn()));

        repository.save((RouletteGame) rouletteGameModel);

        return Optional.of(rouletteGameView);
        }

    }
