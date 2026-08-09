package roulette_service.GameLogic;

import org.junit.jupiter.api.Test;

import roulette_service.Gamelogic.ERouletteGameType;

public class SingleTest {
      @Test
      void single_winning(){
            ERouletteGameType gameType = ERouletteGameType.SINGLE;
            int[] bet = new int[]{1};
            int ballPosition = 1;
            int expectedReturn = 36;
            RouletteTest.testWinning(gameType,bet,ballPosition,expectedReturn);
      }

      @Test
      void single_losing(){
            ERouletteGameType gameType = ERouletteGameType.SINGLE;
            int[] bet = new int[]{1};
            int ballPosition = 10;
            RouletteTest.testLosing(gameType,bet,ballPosition);
      }
}
