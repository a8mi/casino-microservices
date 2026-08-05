package roulette_service.Gamelogic;

import java.util.Random;
import java.util.Set;

import roulette_service.Requests.IRouletteGameStartRequest;

public class BlackGame implements IRouletteGameLogic{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float betReturn;
    private int result;

    private BlackGame(IRouletteGameStartRequest rouletteGameStartRequest){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float wager = rouletteGameStartRequest.getWager();
        Random random = new Random();
        this.result = random.nextInt(37);

        Set<Integer> redNums = Set.of(1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36);
    
        this.isWin = !redNums.contains(this.result) && this.result != 0;
        
        this.betReturn = this.isWin? wager * 2 : 0;
    
    }

    public static IRouletteGameLogic create(IRouletteGameStartRequest rouletteGameStartRequest) {
        BlackGame blackGame = new BlackGame(rouletteGameStartRequest);
        return blackGame;
    }

    @Override
    public int[] getBet(){
        return this.bet;
    }

    @Override
    public boolean getIsWin() {
        return this.isWin;
    }

    @Override
    public float getBetReturn() {
        return this.betReturn;
    }

    @Override
    public int getResult(){
        return this.result;
    }
    
}
