package slotmachine_service.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import slotmachine_service.Handler.ISlotMachineHandler;
import slotmachine_service.View.GameView;
import slotmachine_service.View.StatsView;
import slotmachine_service.View.PlayRequest;
import slotmachine_service.View.UserStatsView;

import java.util.List;

@RestController
public class SlotMachineController implements ISlotMachineController{
    private final ISlotMachineHandler handler;

    public SlotMachineController(ISlotMachineHandler handler) {
        this.handler = handler;
    }
    
    public ResponseEntity <GameView> playGame(PlayRequest request) {
        return ResponseEntity.ok(handler.playGame(request));
    }

    public ResponseEntity <String> getRules() {
        return ResponseEntity.ok(handler.getRules());
    }

    public ResponseEntity <String> getChances() {
        return ResponseEntity.ok(handler.getChances());
    }

    public ResponseEntity <StatsView> getStats() {
        return ResponseEntity.ok(handler.getStats());
    }

    public ResponseEntity <UserStatsView> getUserStatsById(Long userId) {
        return ResponseEntity.ok(handler.getUserStatsById(userId));
    }

    public ResponseEntity<List<GameView>> getAllGames() {
        return ResponseEntity.ok(handler.getAllGames());
    }

    public ResponseEntity<GameView> getGameById(Long gameId) {
        return ResponseEntity.ok(handler.getGameById(gameId));
    }

    public ResponseEntity<GameView> deleteGame(Long gameId) {
        return ResponseEntity.ok(handler.deleteGame(gameId));
    }
}
