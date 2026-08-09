package roulette_service.Controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roulette_service.Handler.IRouletteHandler;
import roulette_service.Requests.RouletteGameStartRequest;

public class RouletteControllerTest {
      
      private RouletteController controller;
      private IRouletteHandler handler;

      @BeforeEach
      void setUp(){
            handler = mock(IRouletteHandler.class);
            controller = new RouletteController(handler);
      }   

      @Test
      void playGame_calls_handler(){
            RouletteGameStartRequest req = mock(RouletteGameStartRequest.class);
            controller.playGame(req);
            verify(handler).playGame(req);
      }

      @Test
      void getRules_calls_handler(){
            controller.getRules();
            verify(handler).getRules();
      }

      @Test
      void getChances_calls_handler(){
            controller.getChances();
            verify(handler).getChances();
      }

      @Test
      void getStats_calls_handler(){
            controller.getStats();
            verify(handler).getStats();
      }

      @Test
      void getAllGames_calls_handler(){
            controller.getAllGames();
            verify(handler).getAllGames();
      }

      @Test
      void getGamebyId_calls_handler(){
            controller.getGameById(12L);
            verify(handler).getGameById(12L);
      }

      @Test
      void deleteGame_calls_handler(){
            controller.deleteGame(12L);
            verify(handler).deleteGame(12L);
      }
}
