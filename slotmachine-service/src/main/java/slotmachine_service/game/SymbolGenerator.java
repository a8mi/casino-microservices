package slotmachine_service.game;

import slotmachine_service.model.SlotSymbol;

import java.util.List;

public interface SymbolGenerator {
    List<SlotSymbol> spin();
}
