package slotmachine_service.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import slotmachine_service.Handler.ISlotMachineHandler;
import slotmachine_service.View.IGameView;
import slotmachine_service.View.IPlayRequest;
import slotmachine_service.View.IStatsView;
import slotmachine_service.View.IUserStatsView;

import java.util.List;

@RestController
public class SlotMachineController implements ISlotMachineController{
    private final ISlotMachineHandler handler;

    public SlotMachineController(ISlotMachineHandler handler) {
        this.handler = handler;
    }
    
    public ResponseEntity <IGameView> playGame(IPlayRequest request) {
        return ResponseEntity.ok(handler.playGame(request));
    }

    public ResponseEntity <String> getRules() {
        return ResponseEntity.ok(handler.getRules());
    }

    public ResponseEntity <String> getChances() {
        return ResponseEntity.ok(handler.getChances());
    }

    public ResponseEntity <IStatsView> getStats() {
        return ResponseEntity.ok(handler.getStats());
    }

    public ResponseEntity <IUserStatsView> getUserStatsById(Long userId) {
        return ResponseEntity.ok(handler.getUserStatsById(userId));
    }

    public ResponseEntity<List<IGameView>> getAllGames() {
        return ResponseEntity.ok(handler.getAllGames());
    }

    public ResponseEntity<IGameView> getGameById(Long gameId) {
        return ResponseEntity.ok(handler.getGameById(gameId));
    }

    public ResponseEntity<IGameView> deleteGame(Long gameId) {
        return ResponseEntity.ok(handler.deleteGame(gameId));
    }
}
