package slotmachine_service.GameLogic;

import java.util.List;

import slotmachine_service.Model.ESlotSymbol;

public interface ISymbolGenerator {
    List<ESlotSymbol> spin();
}
