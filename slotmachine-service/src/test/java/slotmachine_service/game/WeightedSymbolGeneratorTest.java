package slotmachine_service.game;

import org.junit.jupiter.api.Test;
import slotmachine_service.model.SlotSymbol;

import java.util.random.RandomGenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WeightedSymbolGeneratorTest {

    @Test
    void mapsWeightedTicketBoundariesToExpectedSymbols() {
        RandomGenerator random = mock(RandomGenerator.class);
        when(random.nextInt(100)).thenReturn(0, 30, 99);

        WeightedSymbolGenerator generator = new WeightedSymbolGenerator(random);

        assertThat(generator.spin()).containsExactly(
                SlotSymbol.CHERRY,
                SlotSymbol.LEMON,
                SlotSymbol.SEVEN
        );
        verify(random, times(3)).nextInt(100);
    }
}
