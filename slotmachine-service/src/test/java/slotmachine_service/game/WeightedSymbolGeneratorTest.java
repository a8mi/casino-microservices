package slotmachine_service.game;

import org.junit.jupiter.api.Test;

import slotmachine_service.GameLogic.SymbolGenerator;
import slotmachine_service.Model.ESlotSymbol;

import java.util.random.RandomGenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WeightedSymbolGeneratorTest {

    @Test
    void mapsWeightedTicketBoundariesToExpectedSymbols() {
        RandomGenerator random = mock(RandomGenerator.class);
        when(random.nextInt(100)).thenReturn(0, 30, 99);

        SymbolGenerator generator = new SymbolGenerator(random);

        assertThat(generator.spin()).containsExactly(
                ESlotSymbol.CHERRY,
                ESlotSymbol.LEMON,
                ESlotSymbol.SEVEN
        );
        verify(random, times(3)).nextInt(100);
    }
}
