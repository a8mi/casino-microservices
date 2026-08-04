package roulette_service.Gamelogic;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.Utils.RouletteGameValidation;

public class SixLineGame implements IRouletteGameLogic{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float payout;
    private int result;

    private SixLineGame(IRouletteGameStartRequest rouletteGameStartRequest){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.bet = new int[6];
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float amount = rouletteGameStartRequest.getAmount();
        Random random = new Random();
        this.result = random.nextInt(37);
                
        Set<Integer> betSetSix = new HashSet<Integer>();

        for (int i = 0; i < 6; i++){
            this.bet[i] = (this.rouletteGameStartRequest.getBet()[0] + i);
            betSetSix.add(this.rouletteGameStartRequest.getBet()[0] + i);
        }
        this.isWin = betSetSix.contains(this.result);
        this.payout = this.isWin? amount * 5 : - amount;
    }

    public static IRouletteGameLogic create(IRouletteGameStartRequest rouletteGameStartRequest) {
        int[] userBet = rouletteGameStartRequest.getBet();

        if(userBet.length != 1 ||
            RouletteGameValidation.smallestNumber(userBet) % 3 != 1 ||
            !RouletteGameValidation.validNums(userBet, 0, 33))
            return null;
        
        SixLineGame sixLineGame = new SixLineGame(rouletteGameStartRequest);
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
    public float getPayout() {
        return this.payout;
    }

    @Override
    public int getResult(){
        return this.result;
    }
    


}
