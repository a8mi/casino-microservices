package roulette_service.Gamelogic;

import java.util.Random;
import roulette_service.Requests.IRouletteGameStartRequest;

public class SplitGame implements IRouletteGameLogic{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float betReturn;
    private int result;

    private SplitGame(IRouletteGameStartRequest rouletteGameStartRequest){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.bet = new int[] {rouletteGameStartRequest.getBet()[0], rouletteGameStartRequest.getBet()[1]};
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float wager = rouletteGameStartRequest.getWager();
        Random random = new Random();
        this.result = random.nextInt(37);
        
        this.isWin = (this.result == this.bet[0]) || (this.result == this.bet[1]);
        this.betReturn = isWin? wager * 18 : 0;
    }

    public static IRouletteGameLogic create(IRouletteGameStartRequest rouletteGameStartRequest) {
        int[] userBet = rouletteGameStartRequest.getBet();

        int smallerNum = Math.min(userBet[0], userBet[1]);
        int biggerNum = Math.max(userBet[0], userBet[1]);
        int diffNums = Math.abs(userBet[0] - userBet[1]);

        boolean nonZero = smallerNum != 0;
        boolean nonNeighoringRows = (smallerNum % 3 == 0 && !(biggerNum % 3 == 0));
        boolean nonNeighboringNumbers =  (diffNums != 1 && diffNums != 3);
        boolean zeroButInvalid = (smallerNum == 0 && diffNums > 3);

        if ( userBet.length < 2 || nonZero && (nonNeighoringRows || nonNeighboringNumbers) || zeroButInvalid ){
            return null;
        }
        
        SplitGame splitGame = new SplitGame(rouletteGameStartRequest);
        return splitGame;
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
