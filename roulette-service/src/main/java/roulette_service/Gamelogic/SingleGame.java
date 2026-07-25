package roulette_service.Gamelogic;

import java.util.Random;
import roulette_service.Requests.IRouletteGameStartRequest;

public class SingleGame implements IRouletteGame{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float payout;
    private int result;

    private SingleGame(IRouletteGameStartRequest rouletteGameStartRequest){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.bet = new int[] {rouletteGameStartRequest.getBet()[0]};
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float amount = rouletteGameStartRequest.getAmount();
        Random random = new Random();
        this.result = random.nextInt(37);
        this.isWin = (this.result == this.bet[0]);
        this.payout = this.isWin? amount * 35 : - amount;
    }

    public static IRouletteGame create(IRouletteGameStartRequest rouletteGameStartRequest) {
        SingleGame singleGame = new SingleGame(rouletteGameStartRequest);
        return singleGame;
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
