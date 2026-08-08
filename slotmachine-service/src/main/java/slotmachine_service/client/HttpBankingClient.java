package slotmachine_service.Client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import slotmachine_service.Config.BankingProperties;
import slotmachine_service.Exceptions.BankingServiceException;
import slotmachine_service.Exceptions.UserNotFoundException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Component
public class HttpBankingClient implements BankingClient {

    private static final String INVOICING_PARTY = "slotmachine-service";

    private final RestClient restClient;
    private final BankingProperties properties;

    public HttpBankingClient(RestClient bankingRestClient, BankingProperties properties) {
        this.restClient = bankingRestClient;
        this.properties = properties;
    }

    @Override
    public UserAccount getUser(Long userId) {
        validateUserId(userId);

        try {
            UserAccount response = restClient.get()
                    .uri(properties.userPath(), Map.of("userId", userId))
                    .retrieve()
                    .body(UserAccount.class);

            if (response == null) {
                throw new BankingServiceException("Banking service returned an empty user response");
            }
            if (!Objects.equals(response.id(), userId)) {
                throw new BankingServiceException("Banking service returned a different user id");
            }
            return response;
        } catch (HttpClientErrorException.NotFound exception) {
            throw new UserNotFoundException(userId);
        } catch (RestClientResponseException exception) {
            if (exception.getStatusCode().value() == 404) {
                throw new UserNotFoundException(userId);
            }
            throw new BankingServiceException(
                    "Banking service rejected user verification with status " + exception.getStatusCode().value(),
                    exception
            );
        } catch (RestClientException exception) {
            throw new BankingServiceException("Banking service is unavailable during user verification", exception);
        }
    }

    @Override
    public void createTransaction(Long userId, BigDecimal amount) {
        validateUserId(userId);
        Objects.requireNonNull(amount, "amount is required");

        Map<String, Object> request = Map.of(
                "invoicingParty", INVOICING_PARTY,
                "amount", amount
        );

        try {
            restClient.post()
                    .uri(properties.transactionPath(), Map.of("userId", userId))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new UserNotFoundException(userId);
        } catch (RestClientResponseException exception) {
            if (exception.getStatusCode().value() == 404) {
                throw new UserNotFoundException(userId);
            }
            throw new BankingServiceException(
                    "Banking service rejected the transaction with status " + exception.getStatusCode().value(),
                    exception
            );
        } catch (RestClientException exception) {
            throw new BankingServiceException("Banking service is unavailable during transaction creation", exception);
        }
    }

    private static void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId must be positive");
        }
    }
}
