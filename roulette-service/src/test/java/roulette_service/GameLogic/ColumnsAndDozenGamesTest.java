package roulette_service.GameLogic;

import org.junit.jupiter.api.Test;

import roulette_service.Gamelogic.ERouletteGameType;

public class ColumnsAndDozenGamesTest {
      @Test
      void column_first_winning(){
            ERouletteGameType gameType = ERouletteGameType.COLUMN;
            int[] bet = {1};
            int ballPosition = 4;
            int expectedReturn = 3;
            RouletteTest.testWinning(gameType, bet, ballPosition, expectedReturn);
      }

      @Test
      void column_first_losing(){
            ERouletteGameType gameType = ERouletteGameType.COLUMN;
            int[] bet = {1};
            int ballPosition = 5;
            RouletteTest.testLosing(gameType, bet, ballPosition);
      }

      @Test
      void column_second_winning(){
            ERouletteGameType gameType = ERouletteGameType.COLUMN;
            int[] bet = {2};
            int ballPosition = 5;
            int expectedReturn = 3;
            RouletteTest.testWinning(gameType, bet, ballPosition, expectedReturn);
      }
      @Test
      void column_second_losing(){
            ERouletteGameType gameType = ERouletteGameType.COLUMN;
            int[] bet = {2};
            int ballPosition = 4;
            RouletteTest.testLosing(gameType, bet, ballPosition);
      }

      @Test
      void column_third_winning(){
            ERouletteGameType gameType = ERouletteGameType.COLUMN;
            int[] bet = {3};
            int ballPosition = 6;
            int expectedReturn = 3;
            RouletteTest.testWinning(gameType, bet, ballPosition, expectedReturn);
      }
      
      @Test
      void column_third_losing(){
            ERouletteGameType gameType = ERouletteGameType.COLUMN;
            int[] bet = {3};
            int ballPosition = 5;
            RouletteTest.testLosing(gameType, bet, ballPosition);
      }

      @Test
      void column_zero_losing(){
            ERouletteGameType gameType = ERouletteGameType.COLUMN;
            int[] bet = {3};
            int ballPosition = 0;
            RouletteTest.testLosing(gameType, bet, ballPosition);
      }

      @Test
      void dozen_first_winning(){
            ERouletteGameType gameType = ERouletteGameType.DOZEN;
            int[] bet = {1};
            int ballPosition = 12;
            int expectedReturn = 3;
            RouletteTest.testWinning(gameType, bet, ballPosition, expectedReturn);
      }

      @Test
      void dozen_first_losing(){
            ERouletteGameType gameType = ERouletteGameType.DOZEN;
            int[] bet = {1};
            int ballPosition = 13;
            RouletteTest.testLosing(gameType, bet, ballPosition);
      }

      @Test
      void dozen_second_winning(){
            ERouletteGameType gameType = ERouletteGameType.DOZEN;
            int[] bet = {2};
            int ballPosition = 13;
            int expectedReturn = 3;
            RouletteTest.testWinning(gameType, bet, ballPosition, expectedReturn);
      }

      @Test
      void dozen_second_losing(){
            ERouletteGameType gameType = ERouletteGameType.DOZEN;
            int[] bet = {2};
            int ballPosition = 12;
            RouletteTest.testLosing(gameType, bet, ballPosition);
      }

      @Test
      void dozen_third_winning(){
            ERouletteGameType gameType = ERouletteGameType.DOZEN;
            int[] bet = {3};
            int ballPosition = 25;
            int expectedReturn = 3;
            RouletteTest.testWinning(gameType, bet, ballPosition, expectedReturn);
      }

      @Test
      void dozen_third_losing(){
            ERouletteGameType gameType = ERouletteGameType.DOZEN;
            int[] bet = {3};
            int ballPosition = 24;
            RouletteTest.testLosing(gameType, bet, ballPosition);
      }


      @Test
      void dozen_zero_losing(){
            ERouletteGameType gameType = ERouletteGameType.DOZEN;
            int[] bet = {3};
            int ballPosition = 0;
            RouletteTest.testLosing(gameType, bet, ballPosition);
      }

      @Test
      void dozen_over_three_invalid(){
            ERouletteGameType gameType = ERouletteGameType.DOZEN;
            int[] bet = new int[]{4};
            int ballPosition = 36;
            RouletteTest.assertInvalid(gameType, bet, ballPosition);
      }
}
