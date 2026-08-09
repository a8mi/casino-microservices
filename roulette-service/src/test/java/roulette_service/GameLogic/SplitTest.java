package roulette_service.GameLogic;

import org.junit.jupiter.api.Test;

import roulette_service.Gamelogic.ERouletteGameType;

public class SplitTest {
            @Test
      void split_winning_same_row(){
            ERouletteGameType gameType = ERouletteGameType.SPLIT;
            int[] bet = new int[]{1,2};
            int ballPosition = 1;
            int expectedReturn = 18;   
            for (int i = 0; i < 2; i++){
                  RouletteTest.testWinning(gameType, bet, ballPosition, expectedReturn);
            } 
      }

      @Test
      void split_winning_same_third_column(){
            ERouletteGameType gameType = ERouletteGameType.SPLIT;
            int[] bet = new int[]{3,6};
            int ballPosition = 3;
            int expectedReturn = 18;   
            for (int i = 0; i < 4; i +=3){
                  RouletteTest.testWinning(gameType, bet, ballPosition, expectedReturn);
            } 
      }

      @Test
      void split_losing(){
            ERouletteGameType gameType = ERouletteGameType.SPLIT;
            int[] bet = new int[]{1,4};
            int ballPosition = 30;
            RouletteTest.testLosing(gameType,bet,ballPosition);
      }

      @Test
      void split_third_and_first_column_invalid(){
            ERouletteGameType gameType = ERouletteGameType.SPLIT;
            int[] bet = new int[]{3,4};
            int ballPosition = 3;
            RouletteTest.assertInvalid(gameType, bet, ballPosition);
      }
}
