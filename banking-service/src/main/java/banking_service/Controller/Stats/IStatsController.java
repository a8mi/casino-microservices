package banking_service.Controller.Stats;

import banking_service.View.Stats.StatsResponse;
import banking_service.View.Stats.UserStatsResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/casino/bank/api")
public interface IStatsController {

    @GetMapping("/stats")
    StatsResponse getGlobalStats();

    @GetMapping("/stats/user/{id}")
    UserStatsResponse getUserStats(@PathVariable Long id);
}