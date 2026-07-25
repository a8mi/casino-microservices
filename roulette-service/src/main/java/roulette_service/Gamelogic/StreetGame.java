package roulette_service.Gamelogic;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.Utils.RouletteValidation;

public class StreetGame implements IRouletteGame{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float payout;
    private int result;

    private StreetGame(IRouletteGameStartRequest rouletteGameStartRequest){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.bet = new int[3];
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float amount = rouletteGameStartRequest.getAmount();
        Random random = new Random();
        this.result = random.nextInt(37);


        Set<Integer> betSetStreet = new HashSet<Integer>();

        for (int i = 0; i < 3; i++){
            betSetStreet.add(rouletteGameStartRequest.getBet()[i]);
            this.bet[i] = rouletteGameStartRequest.getBet()[i];
        }
        this.isWin = betSetStreet.contains(this.result);
        this.payout = this.isWin? amount * 11 : - amount;
        
    }

    public static IRouletteGame create(IRouletteGameStartRequest rouletteGameStartRequest) {
        int[] userBet = rouletteGameStartRequest.getBet();

        int smallestNumber = RouletteValidation.smallestNumber(userBet);
        int numSum = 0;
        for (int num : userBet){
            numSum += num;
        }

        boolean invalidZero = (smallestNumber == 0) && 
                                (!RouletteValidation.validNums(userBet, 0, 3) || (numSum != 3 && numSum != 5));
        boolean invalidNonZero = (smallestNumber != 0) &&
                                    (! RouletteValidation.validNums(userBet, smallestNumber, smallestNumber + 2) ||
                                    !(RouletteValidation.hasIncrementOne(userBet)) || smallestNumber % 3 != 1 );

        if (invalidZero || invalidNonZero) return null;
        
        StreetGame streetGame = new StreetGame(rouletteGameStartRequest);
        return streetGame;
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
