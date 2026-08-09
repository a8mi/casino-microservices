package roulette_service.GameLogic;

import org.junit.jupiter.api.Test;

import roulette_service.Gamelogic.ERouletteGameType;

public class SixlineTest {
      @Test
      void sixline_winning(){
            ERouletteGameType gameType = ERouletteGameType.SIXLINE;
            int[] bet = new int[]{1};
            int ballPosition = 1;
            int expectedReturn = 6;   
            for (int i = 0; i < 6; i++){
                  RouletteTest.testWinning(gameType,bet,ballPosition + i,expectedReturn);
            } 
      }

      @Test
      void sixline_losing(){
            ERouletteGameType gameType = ERouletteGameType.SIXLINE;
            int[] bet = new int[]{1};
            int ballPosition = 7;
            RouletteTest.testLosing(gameType,bet,ballPosition);    
      }

      @Test
      void sixLine_thirtyfour_invalid(){
            ERouletteGameType gameType = ERouletteGameType.SIXLINE;
            int[] bet = new int[]{34};
            int ballPosition = 34;
            RouletteTest.assertInvalid(gameType, bet, ballPosition);
      }

      @Test
      void sixLine_zero_invalid(){
            ERouletteGameType gameType = ERouletteGameType.SIXLINE;
            int[] bet = new int[]{0};
            int ballPosition = 0;
            RouletteTest.assertInvalid(gameType, bet, ballPosition);
      }

      @Test
      void sixLine_second_column_invalid(){
            ERouletteGameType gameType = ERouletteGameType.SIXLINE;
            int[] bet = new int[]{29};
            int ballPosition = 29;
            RouletteTest.assertInvalid(gameType, bet, ballPosition);
      }

      @Test
      void sixLine_third_column_invalid(){
            ERouletteGameType gameType = ERouletteGameType.SIXLINE;
            int[] bet = new int[]{30};
            int ballPosition = 30;
            RouletteTest.assertInvalid(gameType, bet, ballPosition);
      }

}
