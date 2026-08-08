package slotmachine_service.Controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import slotmachine_service.View.IGameView;
import slotmachine_service.View.PlayRequest;
import slotmachine_service.View.IStatsView;
import slotmachine_service.View.IUserStatsView;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/casino/slots/api")
public interface ISlotMachineController {

    @PostMapping("/play")
    @Operation(summary = "Play one complete slot-machine round")
    ResponseEntity<IGameView> playGame(@Valid @RequestBody PlayRequest request);

    @GetMapping(value = "/info/rules", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Show the game rules")
    ResponseEntity<String> getRules();

    @GetMapping(value = "/info/chances", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Show symbol probabilities, payouts, RTP and house edge")
    ResponseEntity<String> getChances();

    @GetMapping("/stats")
    @Operation(summary = "Show aggregate slot-machine statistics")
    ResponseEntity<IStatsView> getStats();

    @GetMapping("/stats/user/{user_id}")
    @Operation(summary = "Show statistics for one user")
    ResponseEntity<IUserStatsView> getUserStatsById(@PathVariable("user_id") Long userId);

    @GetMapping("/stats/games")
    @Operation(summary = "List all played slot-machine games")
    ResponseEntity<List<IGameView>> getAllGames();

    @GetMapping("/stat/{game_id}")
    @Operation(summary = "Get one slot-machine game")
    ResponseEntity<IGameView> getGameById(@PathVariable("game_id") Long gameId);

    @DeleteMapping("/stat/{game_id}")
    @Operation(summary = "Delete one local game-history entry without reversing its bank transaction")
    ResponseEntity<IGameView> deleteGame(@PathVariable("game_id") Long gameId);
}
