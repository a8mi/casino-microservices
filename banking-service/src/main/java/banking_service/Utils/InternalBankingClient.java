package banking_service.Utils;

import banking_service.View.Transactions.TransactionResponse;
import banking_service.View.User.UserView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class InternalBankingClient implements IInternalBankingClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public InternalBankingClient(
            @Value("${banking.service.base-url:http://localhost:8080}") String baseUrl) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = baseUrl;
    }

    @Override
    public boolean userExists(Long id) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(
                    baseUrl + "/casino/bank/api/user/{id}", String.class, id);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    @Override
    public UserView getUser(Long id) {
        return restTemplate.getForObject(
                baseUrl + "/casino/bank/api/user/{id}", UserView.class, id);
    }

    @Override
    public List<UserView> getAllUsers() {
        UserView[] users = restTemplate.getForObject(
                baseUrl + "/casino/bank/api/users", UserView[].class);
        return users == null ? List.of() : Arrays.asList(users);
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        TransactionResponse[] txs = restTemplate.getForObject(
                baseUrl + "/casino/bank/api/transactions", TransactionResponse[].class);
        return txs == null ? List.of() : Arrays.asList(txs);
    }

    @Override
    public List<TransactionResponse> getTransactionsByUserId(Long userId) {
        TransactionResponse[] txs = restTemplate.getForObject(
                baseUrl + "/casino/bank/api/transactions/user/{id}",
                TransactionResponse[].class, userId);
        return txs == null ? List.of() : Arrays.asList(txs);
    }

    @Override
    public void depositToUser(Long id, BigDecimal amount) {
        long unscaled = amount.unscaledValue().longValueExact();
        int scale = amount.scale();
        restTemplate.postForEntity(
                baseUrl + "/casino/bank/api/user/{id}/deposit/{amount}/{decimals}",
                null, String.class, id, unscaled, scale);
    }
}