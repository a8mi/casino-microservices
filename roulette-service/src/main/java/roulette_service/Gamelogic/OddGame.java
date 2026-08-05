package roulette_service.Gamelogic;

import java.util.Random;

import roulette_service.Requests.IRouletteGameStartRequest;

public class OddGame implements IRouletteGameLogic{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float betReturn;
    private int result;

    private OddGame(IRouletteGameStartRequest rouletteGameStartRequest){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float wager = rouletteGameStartRequest.getWager();
        Random random = new Random();
        this.result = random.nextInt(37);

        this.isWin = result % 2 == 1;

        this.betReturn = this.isWin? wager * 2 : 0;
    
    }

    public static IRouletteGameLogic create(IRouletteGameStartRequest rouletteGameStartRequest) {
        OddGame oddGame = new OddGame(rouletteGameStartRequest);
        return oddGame;
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
