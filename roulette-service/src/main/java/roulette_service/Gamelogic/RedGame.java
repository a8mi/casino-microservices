package roulette_service.Gamelogic;

import java.util.Set;

import roulette_service.Requests.IRouletteGameStartRequest;

public class RedGame implements IRouletteGameLogic{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float betReturn;
    private int ballPosition;

    private RedGame(IRouletteGameStartRequest rouletteGameStartRequest, int ballPosition){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.ballPosition = ballPosition;
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float wager = rouletteGameStartRequest.getWager();

        Set<Integer> redNums = Set.of(1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36);
    
        this.isWin = redNums.contains(this.ballPosition);
        
        this.betReturn = this.isWin? wager * 2: 0;
    
    }

    public static IRouletteGameLogic create(IRouletteGameStartRequest rouletteGameStartRequest, int ballPosition) {
        RedGame redGame = new RedGame(rouletteGameStartRequest, ballPosition);
        return redGame;
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
    public int getBallPosition(){
        return this.ballPosition;
    }
    


}
