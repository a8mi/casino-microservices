package slotmachine_service.game;

import org.springframework.stereotype.Component;
import slotmachine_service.model.SlotSymbol;

import java.util.Arrays;
import java.util.List;
import java.util.random.RandomGenerator;

@Component
public class WeightedSymbolGenerator implements SymbolGenerator {

    private static final int REEL_COUNT = 3;

    private final RandomGenerator randomGenerator;
    private final List<SlotSymbol> symbols;
    private final int totalWeight;

    public WeightedSymbolGenerator(RandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
        this.symbols = List.copyOf(Arrays.asList(SlotSymbol.values()));
        this.totalWeight = symbols.stream().mapToInt(SlotSymbol::weight).sum();

        if (totalWeight <= 0) {
            throw new IllegalStateException("symbol weights must have a positive total");
        }
    }

    @Override
    public List<SlotSymbol> spin() {
        return List.of(draw(), draw(), draw());
    }

    private SlotSymbol draw() {
        int ticket = randomGenerator.nextInt(totalWeight);
        int upperBound = 0;

        for (SlotSymbol symbol : symbols) {
            upperBound += symbol.weight();
            if (ticket < upperBound) {
                return symbol;
            }
        }

        throw new IllegalStateException("weighted symbol selection failed");
    }
}
