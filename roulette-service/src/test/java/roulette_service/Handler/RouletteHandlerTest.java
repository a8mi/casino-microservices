package roulette_service.Handler;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import roulette_service.Client.IRouletteHTTPClient;
import roulette_service.Client.RouletteHTTPClient;
import roulette_service.Client.User;
import roulette_service.Model.IRouletteGameFactory;
import roulette_service.Model.RouletteGameFactory;
import roulette_service.Repository.IRouletteGameRepository;
import roulette_service.Requests.RouletteGameStartRequest;
import roulette_service.Utils.RouletteGameConstants;
import roulette_service.View.IRoulettePlayGameView;

public class RouletteHandlerTest {

      private IRouletteGameRepository repository;
      private IRouletteHTTPClient client;
      private IRouletteGameFactory modelFactory;
      private IRouletteHandler handler;

      @BeforeEach
      void setUp(){
            client = mock(RouletteHTTPClient.class);
            repository = mock(IRouletteGameRepository.class);
            modelFactory = new RouletteGameFactory();
            handler = new RouletteHandler(repository,modelFactory,client);
      }

      @Test
      void playGame_notEnoughMoney_fails(){
            when(client.getUserById(1L)).
            thenReturn(
            new User(1L, "John", "Doe", BigDecimal.ZERO));
            Optional<IRoulettePlayGameView> result = handler.playGame(new RouletteGameStartRequest(1L, "single", new int[]{3},20));
            assertTrue(result.isEmpty());
      }

      @Test
      void playGame_noUserWithId_fails(){
            when(client.getUserById(1L)).
            thenReturn(null);
            Optional<IRoulettePlayGameView> result = handler.playGame(new RouletteGameStartRequest(1L, "single", new int[]{3},20));
            assertTrue(result.isEmpty());
      }

      @Test
      void getRules_works(){
            String actual =  handler.getRules();
            String expected = RouletteGameConstants.RULES;
            assertTrue(actual.equals(expected));
      }

      @Test
      void getChances_works(){
            String actual =  handler.getChances();
            String expected = RouletteGameConstants.CHANCES;
            assertTrue(actual.equals(expected));
      }

}
