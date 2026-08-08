package slotmachine_service.Handler;

import java.util.List;

import slotmachine_service.View.IGameView;
import slotmachine_service.View.IPlayRequest;
import slotmachine_service.View.IStatsView;
import slotmachine_service.View.IUserStatsView;

public interface ISlotMachineHandler {
    IGameView playGame(IPlayRequest request);
    String getRules();
    String getChances();
    IStatsView getStats();
    IUserStatsView getUserStatsById(Long userId);
    List<IGameView> getAllGames();
    IGameView getGameById(Long gameId);
    IGameView deleteGame(Long gameId);
}
