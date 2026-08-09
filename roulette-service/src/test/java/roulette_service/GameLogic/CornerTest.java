package roulette_service.GameLogic;

import org.junit.jupiter.api.Test;

import roulette_service.Gamelogic.ERouletteGameType;

public class CornerTest {

      @Test
      void corner_winning(){
            ERouletteGameType gameType = ERouletteGameType.CORNER;
            int[] bet = new int[]{1};
            int ballPosition = 1;
            int expectedReturn = 9;   
            for (int i = 0; i < 5; i++){
                  if (i == 2){
                        RouletteTest.testLosing(gameType,bet,ballPosition + i);
                        continue;
                  }
                  RouletteTest.testWinning(gameType,bet,ballPosition + i, expectedReturn);
            } 
      }

      @Test
      void corner_losing(){
            ERouletteGameType gameType = ERouletteGameType.CORNER;
            int[] bet = new int[]{1};
            int ballPosition = 6;
            RouletteTest.testLosing(gameType,bet,ballPosition);
      }

      @Test
      void corner_zero_winning(){
            ERouletteGameType gameType = ERouletteGameType.CORNER;
            int[] bet = new int[]{0};
            int ballPosition = 0;
            int expectedReturn = 9;
            for (int i = 0; i < 4; i++){
                  RouletteTest.testWinning(gameType,bet,ballPosition + i,expectedReturn);
            }  
      }

      @Test
      void corner_third_column_invalid(){
            ERouletteGameType gameType = ERouletteGameType.CORNER;
            int[] bet = new int[]{3};
            int ballPosition = 3;
             RouletteTest.assertInvalid(gameType, bet, ballPosition);
      }

      @Test
      void corner_thirtyfour_invalid(){
            ERouletteGameType gameType = ERouletteGameType.CORNER;
            int[] bet = new int[]{34};
            int ballPosition = 34;
            RouletteTest.assertInvalid(gameType,bet,ballPosition);
      }
      
}
