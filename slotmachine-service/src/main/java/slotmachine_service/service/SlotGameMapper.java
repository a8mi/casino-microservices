package slotmachine_service.service;

import org.springframework.stereotype.Component;
import slotmachine_service.api.GameResponse;
import slotmachine_service.model.SlotGame;

@Component
public class SlotGameMapper {

    public GameResponse toResponse(SlotGame game) {
        return new GameResponse(
                game.getId(),
                game.getUserId(),
                game.isWinning(),
                game.getAmount(),
                game.getBet(),
                game.getPayout(),
                game.getSymbols().stream().map(Enum::name).toList(),
                game.getPlayedAt()
        );
    }
}
