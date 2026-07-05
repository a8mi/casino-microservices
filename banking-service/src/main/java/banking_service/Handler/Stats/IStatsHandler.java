package banking_service.Handler.Stats;

import banking_service.View.Stats.StatsResponse;
import banking_service.View.Stats.UserStatsResponse;

public interface IStatsHandler {
    StatsResponse getGlobalStats();
    UserStatsResponse getUserStats(Long userId);
}