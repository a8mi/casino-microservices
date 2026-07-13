package slotmachine_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import slotmachine_service.api.GameResponse;
import slotmachine_service.api.OverallStatsResponse;
import slotmachine_service.api.PlayRequest;
import slotmachine_service.api.UserStatsResponse;
import slotmachine_service.service.GameInfoService;
import slotmachine_service.service.SlotMachineService;
import slotmachine_service.service.SlotStatsService;

import java.util.List;

@RestController
@RequestMapping("/casino/slots/api")
public class SlotMachineController {

    private final SlotMachineService slotMachineService;
    private final SlotStatsService statsService;
    private final GameInfoService infoService;

    public SlotMachineController(
            SlotMachineService slotMachineService,
            SlotStatsService statsService,
            GameInfoService infoService
    ) {
        this.slotMachineService = slotMachineService;
        this.statsService = statsService;
        this.infoService = infoService;
    }

    @PostMapping("/play")
    @Operation(summary = "Play one complete slot-machine round")
    public GameResponse play(@Valid @RequestBody PlayRequest request) {
        return slotMachineService.play(request);
    }

    @GetMapping(value = "/info/rules", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Show the game rules")
    public String rules() {
        return infoService.rules();
    }

    @GetMapping(value = "/info/chances", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Show symbol probabilities, payouts, RTP and house edge")
    public String chances() {
        return infoService.chances();
    }

    @GetMapping("/stats")
    @Operation(summary = "Show aggregate slot-machine statistics")
    public OverallStatsResponse overallStats() {
        return statsService.getOverallStats();
    }

    @GetMapping("/stats/user/{user_id}")
    @Operation(summary = "Show statistics for one user")
    public UserStatsResponse userStats(@PathVariable("user_id") Long userId) {
        return statsService.getUserStats(userId);
    }

    @GetMapping("/stats/games")
    @Operation(summary = "List all played slot-machine games")
    public List<GameResponse> games() {
        return statsService.getGames();
    }

    @GetMapping("/stat/{game_id}")
    @Operation(summary = "Get one slot-machine game")
    public GameResponse game(@PathVariable("game_id") Long gameId) {
        return statsService.getGame(gameId);
    }

    @DeleteMapping("/stat/{game_id}")
    @Operation(summary = "Delete one local game-history entry without reversing its bank transaction")
    public GameResponse deleteGame(@PathVariable("game_id") Long gameId) {
        return statsService.deleteGame(gameId);
    }
}
