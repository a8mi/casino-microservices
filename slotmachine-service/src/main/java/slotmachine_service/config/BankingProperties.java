package slotmachine_service.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;
import java.time.Duration;
import java.util.Objects;

@ConfigurationProperties(prefix = "casino.banking")
public record BankingProperties(
        URI baseUrl,
        String userPath,
        String transactionPath,
        Duration connectTimeout,
        Duration readTimeout
) {
    public BankingProperties {
        Objects.requireNonNull(baseUrl, "casino.banking.base-url is required");
        requireText(userPath, "casino.banking.user-path is required");
        requireText(transactionPath, "casino.banking.transaction-path is required");
        Objects.requireNonNull(connectTimeout, "casino.banking.connect-timeout is required");
        Objects.requireNonNull(readTimeout, "casino.banking.read-timeout is required");

        if (connectTimeout.isNegative() || connectTimeout.isZero()) {
            throw new IllegalArgumentException("connect-timeout must be positive");
        }
        if (readTimeout.isNegative() || readTimeout.isZero()) {
            throw new IllegalArgumentException("read-timeout must be positive");
        }
    }

    private static void requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
