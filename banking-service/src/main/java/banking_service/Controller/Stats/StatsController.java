package banking_service.Controller.Stats;

import banking_service.Handler.Stats.IStatsHandler;
import banking_service.View.Stats.StatsResponse;
import banking_service.View.Stats.UserStatsResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatsController implements IStatsController {

    private final IStatsHandler statsHandler;

    public StatsController(IStatsHandler statsHandler) {
        this.statsHandler = statsHandler;
    }

    @Override
    public StatsResponse getGlobalStats() {
        return statsHandler.getGlobalStats();
    }

    @Override
    public UserStatsResponse getUserStats(Long id) {
        return statsHandler.getUserStats(id);
    }
}