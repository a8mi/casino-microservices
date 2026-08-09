package roulette_service.GameLogic;

import org.junit.jupiter.api.Test;

import roulette_service.Gamelogic.ERouletteGameType;

public class StreetTest {
      
      @Test
      void street_win(){
            ERouletteGameType gameType = ERouletteGameType.STREET;
            int[] bet = new int[]{1,2,3};
            int ballPosition = 3;
            int expectedReturn = 12;   
            RouletteTest.testWinning(gameType, bet, ballPosition, expectedReturn);
      }

      @Test
      void street_losing(){
            ERouletteGameType gameType = ERouletteGameType.STREET;
            int[] bet = new int[]{1,2,3};
            int ballPosition = 5;
            RouletteTest.testLosing(gameType, bet, ballPosition);
      }

      @Test
      void street_invalid_not_incrementing_nums(){
            ERouletteGameType gameType = ERouletteGameType.STREET;
            int[] bet = new int[]{1,2,5};
            int ballPosition = 3;
            RouletteTest.assertInvalid(gameType, bet, ballPosition);
      }

      @Test
      void street_invalid_first_num_second_column(){
            ERouletteGameType gameType = ERouletteGameType.STREET;
            int[] bet = new int[]{2,3,4};
            int ballPosition = 3;
            RouletteTest.assertInvalid(gameType, bet, ballPosition);
      }

      @Test
      void street_invalid_first_num_third_column(){
            ERouletteGameType gameType = ERouletteGameType.STREET;
            int[] bet = new int[]{3,4,5};
            int ballPosition = 4;
            RouletteTest.assertInvalid(gameType, bet, ballPosition);
      }

      @Test
      void street_zero_one_two_valid(){
            ERouletteGameType gameType = ERouletteGameType.STREET;
            int[] bet = new int[]{0,1,2};
            int ballPosition = 2;
            RouletteTest.assertValid(gameType, bet, ballPosition);
      }

      @Test
      void street_zero_two_three_valid(){
            ERouletteGameType gameType = ERouletteGameType.STREET;
            int[] bet = new int[]{0,2,3};
            int ballPosition = 2; 
            RouletteTest.assertValid(gameType, bet, ballPosition);
      }

      @Test
      void street_zero_one_three_invalid(){
            ERouletteGameType gameType = ERouletteGameType.STREET;
            int[] bet = new int[]{0,1,3};
            int ballPosition = 3;
            RouletteTest.assertInvalid(gameType, bet, ballPosition);
      }

      @Test
      void street_zero_nonincremental_nums_invalid(){
            ERouletteGameType gameType = ERouletteGameType.STREET;
            int[] bet = new int[]{0,1,10};
            int ballPosition = 1;
            RouletteTest.assertInvalid(gameType, bet, ballPosition);
      }
}
