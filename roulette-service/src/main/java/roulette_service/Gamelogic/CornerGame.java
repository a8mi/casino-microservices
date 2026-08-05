package roulette_service.Gamelogic;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.Utils.RouletteGameValidation;

public class CornerGame implements IRouletteGameLogic{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float betReturn;
    private int result;

    private CornerGame(IRouletteGameStartRequest rouletteGameStartRequest){
        int[] userBet = rouletteGameStartRequest.getBet();
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.bet = new int[] {userBet[0], userBet[0] + 1, userBet[0] + 3, userBet[0] + 4};
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float wager = rouletteGameStartRequest.getWager();
        Random random = new Random();
        this.result = random.nextInt(37);
        Set<Integer> betSet = new HashSet<Integer>();

        for (int i = 0; i < 4; i++){
            betSet.add(this.bet[i]);
        }

        this.isWin = betSet.contains(this.result);
        this.betReturn = this.isWin? wager * 9 : 0;
    }

    public static IRouletteGameLogic create(IRouletteGameStartRequest rouletteGameStartRequest) {
        int[] userBet = rouletteGameStartRequest.getBet();

        if (userBet.length != 1 || 
            RouletteGameValidation.smallestNumber(userBet) % 3 == 0 ||
            !RouletteGameValidation.validNums(userBet, 0, 33)) 
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
    public float getBetReturn() {
        return this.betReturn;
    }

    @Override
    public int getResult(){
        return this.result;
    }
    


}
