package roulette_service.Gamelogic;

import java.util.Random;

import roulette_service.Requests.IRouletteGameStartRequest;

public class EvenGame implements IRouletteGame{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float payout;
    private int result;

    private EvenGame(IRouletteGameStartRequest rouletteGameStartRequest){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float amount = rouletteGameStartRequest.getAmount();
        Random random = new Random();
        this.result = random.nextInt(37);

        this.isWin = result!= 0 && result % 2 == 0;

        this.payout = this.isWin? amount : - amount;
    
    }

    public static IRouletteGame create(IRouletteGameStartRequest rouletteGameStartRequest) {
        EvenGame evenGame = new EvenGame(rouletteGameStartRequest);
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
    public float getPayout() {
        return this.payout;
    }

    @Override
    public int getResult(){
        return this.result;
    }
    


}
