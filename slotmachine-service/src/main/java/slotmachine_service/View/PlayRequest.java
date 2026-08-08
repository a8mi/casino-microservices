package slotmachine_service.View;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PlayRequest(
        @NotNull @Positive Long user,
        @NotNull
        @DecimalMin(value = "0.01")
        @DecimalMax(value = "1000.00")
        @Digits(integer = 4, fraction = 2)
        BigDecimal bet
) {
}
