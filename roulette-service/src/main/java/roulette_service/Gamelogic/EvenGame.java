package roulette_service.Gamelogic;

import roulette_service.Requests.IRouletteGameStartRequest;

public class EvenGame implements IRouletteGameLogic{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float betReturn;
    private int ballPosition;

    private EvenGame(IRouletteGameStartRequest rouletteGameStartRequest, int ballPosition){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.ballPosition = ballPosition;
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float wager = rouletteGameStartRequest.getWager();

        this.isWin = ballPosition!= 0 && ballPosition % 2 == 0;

        this.betReturn = this.isWin? wager * 2 : 0;
    
    }

    public static IRouletteGameLogic create(IRouletteGameStartRequest rouletteGameStartRequest, int ballPosition) {
        EvenGame evenGame = new EvenGame(rouletteGameStartRequest, ballPosition);
        return evenGame;
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
