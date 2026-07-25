package roulette_service.Gamelogic;

import java.util.Random;

import roulette_service.Requests.IRouletteGameStartRequest;

public class OddGame implements IRouletteGame{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float payout;
    private int result;

    private OddGame(IRouletteGameStartRequest rouletteGameStartRequest){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float amount = rouletteGameStartRequest.getAmount();
        Random random = new Random();
        this.result = random.nextInt(37);

        this.isWin = result % 2 == 1;

        this.payout = this.isWin? amount : - amount;
    
    }

    public static IRouletteGame create(IRouletteGameStartRequest rouletteGameStartRequest) {
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
    public float getPayout() {
        return this.payout;
    }

    @Override
    public int getResult(){
        return this.result;
    }
    


}
