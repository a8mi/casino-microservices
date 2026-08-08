package slotmachine_service.GameLogic;

import org.springframework.stereotype.Component;

import slotmachine_service.Model.ESlotSymbol;

import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;

@Component
public class SymbolGenerator implements ISymbolGenerator {

    private final RandomGenerator randomGenerator;
    private final List<ESlotSymbol> symbols;
    private final int totalWeight;

    public SymbolGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
        this.symbols = List.copyOf(Arrays.asList(ESlotSymbol.values()));
        this.totalWeight = symbols.stream().mapToInt(ESlotSymbol::weight).sum();

        if (totalWeight <= 0) {
            throw new IllegalStateException("symbol weights must have a positive total");
        }
    }

    @Override
    public List<ESlotSymbol> spin() {
        return List.of(draw(), draw(), draw());
    }

    private ESlotSymbol draw() {
        int ticket = randomGenerator.nextInt(totalWeight);
        int upperBound = 0;

        for (ESlotSymbol symbol : symbols) {
            upperBound += symbol.weight();
            if (ticket < upperBound) {
                return symbol;
            }
        }

        throw new IllegalStateException("weighted symbol selection failed");
    }
}
