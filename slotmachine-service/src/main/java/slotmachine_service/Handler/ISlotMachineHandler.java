package slotmachine_service.Handler;

import java.util.List;

import slotmachine_service.View.GameView;
import slotmachine_service.View.StatsView;
import slotmachine_service.View.PlayRequest;
import slotmachine_service.View.UserStatsView;

public interface ISlotMachineHandler {
    GameView playGame(PlayRequest request);
    String getRules();
    String getChances();
    StatsView getStats();
    UserStatsView getUserStatsById(Long userId);
    List<GameView> getAllGames();
    GameView getGameById(Long gameId);
    GameView deleteGame(Long gameId);
}
