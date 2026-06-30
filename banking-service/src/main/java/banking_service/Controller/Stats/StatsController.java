package banking_service.Controller.Stats;

import banking_service.Handler.Stats.StatsHandler;
import banking_service.View.Stats.StatsResponse;
import banking_service.View.Stats.UserStatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StatsController {

    private final StatsHandler statsHandler;

    @GetMapping("/stats")
    public StatsResponse getGlobalStats() {
        return statsHandler.getGlobalStats();
    }

    @GetMapping("/stats/user/{id}")
    public UserStatsResponse getUserStats(@PathVariable Long id) {
        return statsHandler.getUserStats(id);
    }
}
