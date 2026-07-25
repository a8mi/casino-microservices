package roulette_service.Gamelogic;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.Utils.RouletteValidation;

public class CornerGame implements IRouletteGame{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float payout;
    private int result;

    private CornerGame(IRouletteGameStartRequest rouletteGameStartRequest){
        int[] userBet = rouletteGameStartRequest.getBet();
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.bet = new int[] {userBet[0], userBet[0] + 1, userBet[0] + 3, userBet[0] + 4};
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float amount = rouletteGameStartRequest.getAmount();
        Random random = new Random();
        this.result = random.nextInt(37);
        Set<Integer> betSet = new HashSet<Integer>();

        for (int i = 0; i < 4; i++){
            betSet.add(this.bet[i]);
        }

        this.isWin = betSet.contains(this.result);
        this.payout = this.isWin? amount * 8 : - amount;
    }

    public static IRouletteGame create(IRouletteGameStartRequest rouletteGameStartRequest) {
        int[] userBet = rouletteGameStartRequest.getBet();

        if (userBet.length != 1 || 
            RouletteValidation.smallestNumber(userBet) % 3 == 0 ||
            !RouletteValidation.validNums(userBet, 0, 33)) 
            return null;
        
        CornerGame cornerGame = new CornerGame(rouletteGameStartRequest);
        return cornerGame;
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
