package roulette_service.Gamelogic;

import java.util.HashSet;
import java.util.Set;

import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.Utils.RouletteGameValidation;

public class StreetGame implements IRouletteGameLogic{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float betReturn;
    private int ballPosition;

    private StreetGame(IRouletteGameStartRequest rouletteGameStartRequest, int ballPosition){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.bet = new int[3];
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float wager = rouletteGameStartRequest.getWager();

        Set<Integer> betSetStreet = new HashSet<Integer>();

        for (int i = 0; i < 3; i++){
            betSetStreet.add(rouletteGameStartRequest.getBet()[i]);
            this.bet[i] = rouletteGameStartRequest.getBet()[i];
        }
        this.isWin = betSetStreet.contains(this.ballPosition);
        this.betReturn = this.isWin? wager * 12 : 0 ;
        
    }

    public static IRouletteGameLogic create(IRouletteGameStartRequest rouletteGameStartRequest, int ballPosition) {
        int[] userBet = rouletteGameStartRequest.getBet();

        int smallestNumber = RouletteGameValidation.smallestNumber(userBet);
        int numSum = 0;
        for (int num : userBet){
            numSum += num;
        }

        boolean invalidZero = (smallestNumber == 0) && 
                                (!RouletteGameValidation.validNums(userBet, 0, 3) || (numSum != 3 && numSum != 5));
        boolean invalidNonZero = (smallestNumber != 0) &&
                                    (! RouletteGameValidation.validNums(userBet, smallestNumber, smallestNumber + 2) ||
                                    !(RouletteGameValidation.hasIncrementOne(userBet)) || smallestNumber % 3 != 1 );

        if (invalidZero || invalidNonZero) return null;
        
        StreetGame streetGame = new StreetGame(rouletteGameStartRequest, ballPosition);
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
    public float getBetReturn() {
        return this.betReturn;
    }

    @Override
    public int getBallPosition(){
        return this.ballPosition;
    }
    


}
