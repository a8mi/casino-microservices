package roulette_service.Gamelogic;

import java.util.Random;
import java.util.Set;

import roulette_service.Requests.IRouletteGameStartRequest;

public class RedGame implements IRouletteGame{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float payout;
    private int result;

    private RedGame(IRouletteGameStartRequest rouletteGameStartRequest){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float amount = rouletteGameStartRequest.getAmount();
        Random random = new Random();
        this.result = random.nextInt(37);

        Set<Integer> redNums = Set.of(1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36);
    
        this.isWin = redNums.contains(this.result);
        
        this.payout = this.isWin? amount : - amount;
    
    }

    public static IRouletteGame create(IRouletteGameStartRequest rouletteGameStartRequest) {
        RedGame redGame = new RedGame(rouletteGameStartRequest);
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
    public float getPayout() {
        return this.payout;
    }

    @Override
    public int getResult(){
        return this.result;
    }
    


}
