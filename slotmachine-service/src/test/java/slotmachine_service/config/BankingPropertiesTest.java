package slotmachine_service.config;

import org.junit.jupiter.api.Test;

import slotmachine_service.Config.BankingProperties;

import java.net.URI;
import java.time.Duration;

import static org.assertj.core.api.Assertions.*;

class BankingPropertiesTest {

    @Test
    void acceptsCompletePositiveConfiguration() {
        BankingProperties properties = new BankingProperties(
                URI.create("http://banking:8080"),
                "/casino/bank/api/user/{userId}",
                "/api/transaction/user/{userId}",
                Duration.ofSeconds(2),
                Duration.ofSeconds(3)
        );

        assertThat(properties.baseUrl()).isEqualTo(URI.create("http://banking:8080"));
    }

    @Test
    void rejectsZeroTimeout() {
        assertThatThrownBy(() -> new BankingProperties(
                URI.create("http://banking:8080"),
                "/user/{userId}",
                "/transaction/{userId}",
                Duration.ZERO,
                Duration.ofSeconds(1)
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("connect-timeout");
    }
}
