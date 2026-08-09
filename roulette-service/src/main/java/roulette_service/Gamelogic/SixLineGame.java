package roulette_service.Gamelogic;

import java.util.HashSet;
import java.util.Set;

import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.Utils.RouletteGameValidation;

public class SixLineGame implements IRouletteGameLogic{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float betReturn;
    private int ballPosition;

    private SixLineGame(IRouletteGameStartRequest rouletteGameStartRequest, int ballPosition){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.bet = new int[6];
        this.ballPosition = ballPosition;
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float wager = rouletteGameStartRequest.getWager();
                
        Set<Integer> betSetSix = new HashSet<Integer>();

        for (int i = 0; i < 6; i++){
            this.bet[i] = (this.rouletteGameStartRequest.getBet()[0] + i);
            betSetSix.add(this.rouletteGameStartRequest.getBet()[0] + i);
        }
        this.isWin = betSetSix.contains(this.ballPosition);
        this.betReturn = this.isWin? wager * 6 : 0;
    }

    public static IRouletteGameLogic create(IRouletteGameStartRequest rouletteGameStartRequest, int ballPosition) {
        int[] userBet = rouletteGameStartRequest.getBet();

        if(userBet.length != 1 ||
            RouletteGameValidation.smallestNumber(userBet) % 3 != 1 ||
            !RouletteGameValidation.validNums(userBet, 1, 33))
            return null;
        
        SixLineGame sixLineGame = new SixLineGame(rouletteGameStartRequest, ballPosition);
        return sixLineGame;
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
