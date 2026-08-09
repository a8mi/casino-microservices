package roulette_service.GameLogic;

import org.junit.jupiter.api.Test;

import roulette_service.Gamelogic.ERouletteGameType;

public class NumberlessGamesTest {
      
      @Test
      void red_winning(){
            ERouletteGameType gameType = ERouletteGameType.RED;
            int ballPosition = 9;
            int expectedReturn = 2;
            RouletteTest.testWinning(gameType, ballPosition, expectedReturn);
      }

      @Test
      void red_losing(){
            ERouletteGameType gameType = ERouletteGameType.RED;
            int ballPosition = 8;
            RouletteTest.testLosing(gameType, ballPosition);
      }

      @Test
      void red_zero_losing(){
            ERouletteGameType gameType = ERouletteGameType.RED;
            int ballPosition = 0;
            RouletteTest.testLosing(gameType, ballPosition); 
      }

      @Test
      void black_winning(){
            ERouletteGameType gameType = ERouletteGameType.BLACK;
            int ballPosition = 8;
            int expectedReturn = 2;
            RouletteTest.testWinning(gameType, ballPosition, expectedReturn);
      }

      @Test
      void black_losing(){
            ERouletteGameType gameType = ERouletteGameType.BLACK;
            int ballPosition = 9;
            RouletteTest.testLosing(gameType, ballPosition);
      }

      @Test
      void black_zero_losing(){
            ERouletteGameType gameType = ERouletteGameType.BLACK;
            int ballPosition = 0;
            RouletteTest.testLosing(gameType, ballPosition);
      }

      @Test
      void odd_winning(){
            ERouletteGameType gameType = ERouletteGameType.ODD;
            int ballPosition = 9;
            int expectedReturn = 2;
            RouletteTest.testWinning(gameType, ballPosition, expectedReturn);
      }

      @Test
      void odd_losing(){
            ERouletteGameType gameType = ERouletteGameType.ODD;
            int ballPosition = 8;
            RouletteTest.testLosing(gameType, ballPosition);
      }

      @Test
      void odd_zero_losing(){
            ERouletteGameType gameType = ERouletteGameType.ODD;
            int ballPosition = 0;
            RouletteTest.testLosing(gameType, ballPosition);
      }

      @Test
      void even_winning(){
            ERouletteGameType gameType = ERouletteGameType.EVEN;
            int ballPosition = 8;
            int expectedReturn = 2;
            RouletteTest.testWinning(gameType, ballPosition, expectedReturn);
      }

      @Test
      void even_losing(){
            ERouletteGameType gameType = ERouletteGameType.EVEN;
            int ballPosition = 7;
            RouletteTest.testLosing(gameType, ballPosition);
      }

      @Test
      void even_zero_losing(){
            ERouletteGameType gameType = ERouletteGameType.EVEN;
            int ballPosition = 0;
            RouletteTest.testLosing(gameType, ballPosition);
      }

      @Test
      void low_winning(){
            ERouletteGameType gameType = ERouletteGameType.LOW;
            int ballPosition = 18;
            int expectedReturn = 2;
            RouletteTest.testWinning(gameType, ballPosition, expectedReturn);
      }

      @Test
      void low_losing(){
            ERouletteGameType gameType = ERouletteGameType.LOW;
            int ballPosition = 19;
            RouletteTest.testLosing(gameType, ballPosition);
      }

      @Test
      void low_zero_losing(){
            ERouletteGameType gameType = ERouletteGameType.LOW;
            int ballPosition = 0;
            RouletteTest.testLosing(gameType, ballPosition);
      }

      @Test
      void high_winning(){
            ERouletteGameType gameType = ERouletteGameType.HIGH;
            int ballPosition = 19;
            int expectedReturn = 2;
            RouletteTest.testWinning(gameType, ballPosition, expectedReturn);
      }

      @Test
      void high_losing(){
            ERouletteGameType gameType = ERouletteGameType.HIGH;
            int ballPosition = 18;
            RouletteTest.testLosing(gameType, ballPosition);
      }

      @Test
      void high_zero_losing(){
            ERouletteGameType gameType = ERouletteGameType.HIGH;
            int ballPosition = 0;
            RouletteTest.testLosing(gameType, ballPosition);
      }
}
