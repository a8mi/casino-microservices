package roulette_service.GameLogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import roulette_service.Gamelogic.ERouletteGameType;
import roulette_service.Gamelogic.IRouletteGameLogic;
import roulette_service.Gamelogic.RouletteGameLogicFactory;
import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.Requests.RouletteGameStartRequest;
import roulette_service.Utils.RouletteGameValidation;

public class RouletteGameLogicTest {
      
      private IRouletteGameLogic logic;
      private RouletteGameLogicFactory factory;
      private IRouletteGameStartRequest req;

      @BeforeEach
      void setUp(){
            this.factory = new RouletteGameLogicFactory();
      }

      @Test
      void red_and_winning(){
            ERouletteGameType gameType = ERouletteGameType.RED;
            int ballPosition = 9;
            boolean expectWin = true;
            doTest(gameType, ballPosition, expectWin);
      }

      @Test
      void red_and_losing(){
            ERouletteGameType gameType = ERouletteGameType.RED;
            int ballPosition = 8;
            boolean expectWin = false;
            doTest(gameType, ballPosition, expectWin);
      }

      @Test
      void red_and_zero(){
            testZero(ERouletteGameType.RED);
      }

      @Test
      void black_and_winning(){
            ERouletteGameType gameType = ERouletteGameType.BLACK;
            int ballPosition = 8;
            boolean expectWin = true;
            doTest(gameType, ballPosition, expectWin);
      }

      @Test
      void black_and_losing(){
            ERouletteGameType gameType = ERouletteGameType.BLACK;
            int ballPosition = 9;
            boolean expectWin = false;
            doTest(gameType, ballPosition, expectWin);
      }

      @Test
      void black_and_zero(){
            testZero(ERouletteGameType.BLACK);
      }

      @Test
      void odd_and_winning(){
            ERouletteGameType gameType = ERouletteGameType.ODD;
            int ballPosition = 9;
            boolean expectWin = true;
            doTest(gameType, ballPosition, expectWin);
      }

      @Test
      void odd_and_losing(){
            ERouletteGameType gameType = ERouletteGameType.ODD;
            int ballPosition = 8;
            boolean expectWin = false;
            doTest(gameType, ballPosition, expectWin);
      }

      @Test
      void odd_and_zero(){
            testZero(ERouletteGameType.ODD);
      }

      @Test
      void even_and_winning(){
            ERouletteGameType gameType = ERouletteGameType.EVEN;
            int ballPosition = 8;
            boolean expectWin = true;
            doTest(gameType, ballPosition, expectWin);
      }

      @Test
      void even_and_losing(){
            ERouletteGameType gameType = ERouletteGameType.EVEN;
            int ballPosition = 9;
            boolean expectWin = false;
            doTest(gameType, ballPosition, expectWin);
      }

      @Test
      void even_and_zero(){
            testZero(ERouletteGameType.EVEN);
      }

      @Test
      void low_and_winning(){

            ERouletteGameType gameType = ERouletteGameType.LOW;
            int ballPosition = 5;
            boolean expectWin = true;
            doTest(gameType, ballPosition, expectWin);
      }

      @Test
      void low_and_losing(){
            ERouletteGameType gameType = ERouletteGameType.LOW;
            int ballPosition = 25;
            boolean expectWin = false;
            doTest(gameType, ballPosition, expectWin);

      }

      @Test
      void low_and_zero(){
            testZero(ERouletteGameType.LOW);
      }

      @Test
      void high_and_winning(){
            ERouletteGameType gameType = ERouletteGameType.HIGH;
            int ballPosition = 25;
            boolean expectWin = true;
            doTest(gameType, ballPosition, expectWin);
      }

      @Test
      void high_and_losing(){
            ERouletteGameType gameType = ERouletteGameType.HIGH;
            int ballPosition = 5;
            boolean expectWin = false;
            doTest(gameType, ballPosition, expectWin);
      }

      @Test
      void high_and_zero(){
            testZero(ERouletteGameType.HIGH);
      }

      @Test
      void column_first_win(){
            ERouletteGameType gameType  = ERouletteGameType.COLUMN;
            int[] bet = new int[]{1};
            int ballPosition = 7;
            boolean expectWin = true;
            int expectedReturn = 3;
            
            doTest(gameType,bet,ballPosition,expectWin,expectedReturn);
      }

      @Test
      void column_first_lost(){
            ERouletteGameType gameType  = ERouletteGameType.COLUMN;
            int[] bet = new int[]{1};
            int ballPosition = 5;
            boolean expectWin = false;
            int expectedReturn = 0;
            
            doTest(gameType,bet,ballPosition,expectWin,expectedReturn);
      }

      @Test
      void column_zero(){
            ERouletteGameType gameType  = ERouletteGameType.COLUMN;
            int[] bet = new int[]{1};
            int ballPosition = 0;
            boolean expectWin = false;
            int expectedReturn = 0;
            
            doTest(gameType,bet,ballPosition,expectWin,expectedReturn);
      }

      @Test
      void dozen_first_win(){
            ERouletteGameType gameType  = ERouletteGameType.DOZEN;
            int[] bet = new int[]{1};
            int ballPosition = 12;
            boolean expectWin = true;
            int expectedReturn = 3;
            
            doTest(gameType,bet,ballPosition,expectWin,expectedReturn);
      }

      @Test
      void dozen_first_lost(){
            ERouletteGameType gameType  = ERouletteGameType.DOZEN;
            int[] bet = new int[]{1};
            int ballPosition = 20;
            boolean expectWin = false;
            int expectedReturn = 0;
            
            doTest(gameType,bet,ballPosition,expectWin,expectedReturn);
      }

      @Test
      void dozen_zero(){
            ERouletteGameType gameType  = ERouletteGameType.DOZEN;
            int[] bet = new int[]{1};
            int ballPosition = 0;
            boolean expectWin = false;
            int expectedReturn = 0;
            
            doTest(gameType,bet,ballPosition,expectWin,expectedReturn);
      }

      



      @Test
      void corner_win(){
            ERouletteGameType gameType  = ERouletteGameType.CORNER;
            int[] bet = new int[]{1};
            int ballPosition = 1;
            boolean expectWin = true;
            int expectedReturn = 9;
            
            for (int i = 0; i < 5; i++){
                  if (i == 2){
                        doTest(gameType,bet,ballPosition + i,false,0);
                        continue;
                  }
                  doTest(gameType,bet,ballPosition + i,expectWin,expectedReturn);
            } 
      }

      @Test
      void corner_loss(){
            ERouletteGameType gameType  = ERouletteGameType.CORNER;
            int[] bet = new int[]{1};
            int ballPosition = 6;
            boolean expectWin = false;
            int expectedReturn = 0;
            
            doTest(gameType,bet,ballPosition,expectWin,expectedReturn);
      }

      @Test
      void corner_zero_win(){
            ERouletteGameType gameType  = ERouletteGameType.CORNER;
            int[] bet = new int[]{0};
            int ballPosition = 0;
            boolean expectWin = true;
            int expectedReturn = 9;
            
            for (int i = 0; i < 4; i++){
                  doTest(gameType,bet,ballPosition + i,expectWin,expectedReturn);
            }  
      }


      @Test
      void single_win(){
            ERouletteGameType gameType  = ERouletteGameType.SINGLE;
            int[] bet = new int[]{1};
            int ballPosition = 1;
            boolean expectWin = true;
            int expectedReturn = 36;

            doTest(gameType,bet,ballPosition,expectWin,expectedReturn);
      }



      void doTest( ERouletteGameType betType, int ballPosition, boolean expectWin){
            float wager = 1;
            int[] bet = new int[]{};
            
            req = new RouletteGameStartRequest(3L, betType.name(), bet, wager);
            logic = factory.create(betType, req, ballPosition);
            logic.playGame();

            boolean expectedResult = expectWin? logic.getIsWin() : !logic.getIsWin();
            float expectedReturn = expectWin? 2 : 0;

            assertTrue(expectedResult);
            assertEquals(ballPosition, logic.getBallPosition());
            assertEquals(expectedReturn, logic.getBetReturn());
      }

            
      void doTest( ERouletteGameType betType, int[] bet, int ballPosition, boolean expectWin, float expectedReturn){
            float wager = 1;
            
            req = new RouletteGameStartRequest(3L, betType.name(), bet, wager);
            logic = factory.create(betType, req, ballPosition);
            assertTrue(logic != null);
            logic.playGame();

            boolean expectedResult = expectWin? logic.getIsWin() : !logic.getIsWin();

            assertTrue(expectedResult);
            assertEquals(ballPosition, logic.getBallPosition());
            assertEquals(expectedReturn, logic.getBetReturn());
      }

      void testZero(ERouletteGameType gameType){
            int ballPosition = 0;
            boolean expectWin = false;
            doTest(gameType, ballPosition, expectWin);
      }


}
