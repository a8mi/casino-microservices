package roulette_service.GameLogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import roulette_service.Gamelogic.ERouletteGameType;
import roulette_service.Gamelogic.IRouletteGameLogic;
import roulette_service.Gamelogic.RouletteGameLogicFactory;
import roulette_service.Requests.IRouletteGameStartRequest;
import roulette_service.Requests.RouletteGameStartRequest;

public class RouletteTest {

      private static IRouletteGameLogic logic;
      private static RouletteGameLogicFactory factory;
      private static IRouletteGameStartRequest req;

      static void testWinning( ERouletteGameType betType, int[] bet, int ballPosition, float expectedReturn){
            factory = new RouletteGameLogicFactory();
            float wager = 1;
            
            req = new RouletteGameStartRequest(3L, betType.name(), bet, wager);
            logic = factory.create(betType, req, ballPosition);
            assertTrue(logic != null);
            logic.playGame();

            assertTrue(logic.getIsWin());
            assertEquals(ballPosition, logic.getBallPosition());
            assertEquals(expectedReturn, logic.getBetReturn());
      }

      static void testWinning( ERouletteGameType betType, int ballPosition, float expectedReturn){
            factory = new RouletteGameLogicFactory();
            float wager = 1;
            
            req = new RouletteGameStartRequest(3L, betType.name(), new int[]{0}, wager);
            logic = factory.create(betType, req, ballPosition);
            assertTrue(logic != null);
            logic.playGame();

            assertTrue(logic.getIsWin());
            assertEquals(ballPosition, logic.getBallPosition());
            assertEquals(expectedReturn, logic.getBetReturn());
      }

      static void testLosing( ERouletteGameType betType, int[] bet, int ballPosition){
            factory = new RouletteGameLogicFactory();
            float wager = 1;
            
            req = new RouletteGameStartRequest(3L, betType.name(), bet, wager);
            logic = factory.create(betType, req, ballPosition);
            assertTrue(logic != null);
            logic.playGame();

            assertTrue(!logic.getIsWin());
            assertEquals(ballPosition, logic.getBallPosition());
            assertEquals(0, logic.getBetReturn());
      }

      static void testLosing( ERouletteGameType betType, int ballPosition){
            factory = new RouletteGameLogicFactory();
            float wager = 1;
            
            req = new RouletteGameStartRequest(3L, betType.name(), new int[]{0}, wager);
            logic = factory.create(betType, req, ballPosition);
            assertTrue(logic != null);
            logic.playGame();

            assertTrue(!logic.getIsWin());
            assertEquals(ballPosition, logic.getBallPosition());
            assertEquals(0, logic.getBetReturn());
      }

      static void assertInvalid(ERouletteGameType betType, int[] bet, int ballPosition){
            factory = new RouletteGameLogicFactory();
            float wager = 1;
            
            req = new RouletteGameStartRequest(3L, betType.name(), bet, wager);
            logic = factory.create(betType, req, ballPosition);
            assertEquals(null, logic);
      }

      static void assertValid(ERouletteGameType betType, int[] bet, int ballPosition){
            factory = new RouletteGameLogicFactory();
            float wager = 1;
            
            req = new RouletteGameStartRequest(3L, betType.name(), bet, wager);
            logic = factory.create(betType, req, ballPosition);
            assertNotEquals(null, logic);
      }



      
}
