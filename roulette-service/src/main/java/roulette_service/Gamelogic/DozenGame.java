package roulette_service.Gamelogic;

import java.util.Random;
import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.Utils.RouletteGameValidation;

public class DozenGame implements IRouletteGameLogic{

    private IRouletteGameStartRequest rouletteGameStartRequest;
    private int[] bet;
    private boolean isWin;
    private float betReturn;
    private int result;

    private DozenGame(IRouletteGameStartRequest rouletteGameStartRequest){
        this.rouletteGameStartRequest = rouletteGameStartRequest;
        this.bet = new int[] {rouletteGameStartRequest.getBet()[0]};
        this.isWin = false;
    }

    @Override
    public void playGame() {        

        float wager = rouletteGameStartRequest.getWager();
        Random random = new Random();
        this.result = random.nextInt(37);
    
        if(this.result > 24 ){
            this.isWin = (this.bet[0] == 3);
        } else if (result > 12){
            this.isWin = (this.bet[0] == 2);
        } else if (result > 0){
            this.isWin = (this.bet[0] == 1);
        }
            this.betReturn = this.isWin? wager * 4 : 0;
    }

    public static IRouletteGameLogic create(IRouletteGameStartRequest rouletteGameStartRequest) {        
        int[] userBet = rouletteGameStartRequest.getBet();

        if (userBet.length != 1 || RouletteGameValidation.validNums(userBet, 1, 3)) return null;
        
        DozenGame dozenGame = new DozenGame(rouletteGameStartRequest);
        return dozenGame;
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
