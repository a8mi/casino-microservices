package slotmachine_service.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import slotmachine_service.Client.BankingClient;
import slotmachine_service.Client.HttpBankingClient;
import slotmachine_service.Config.BankingProperties;
import slotmachine_service.Exceptions.UserNotFoundException;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Duration;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class HttpBankingClientTest {

    private MockRestServiceServer server;
    private HttpBankingClient client;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder().baseUrl("http://banking");
        server = MockRestServiceServer.bindTo(builder).build();

        BankingProperties properties = new BankingProperties(
                URI.create("http://banking"),
                "/casino/bank/api/user/{userId}",
                "/api/transaction/user/{userId}",
                Duration.ofSeconds(1),
                Duration.ofSeconds(1)
        );
        client = new HttpBankingClient(builder.build(), properties);
    }

    @Test
    void readsUserBalance() {
        server.expect(once(), requestTo("http://banking/casino/bank/api/user/7"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        """
                        {"id":7,"firstName":"Ada","lastName":"Lovelace","balance":12.50}
                        """,
                        MediaType.APPLICATION_JSON
                ));

        BankingClient.UserAccount account = client.getUser(7L);

        assertThat(account.balance()).isEqualByComparingTo("12.50");
        server.verify();
    }

    @Test
    void createsTransactionUsingCurrentBankingJsonContract() {
        server.expect(once(), requestTo("http://banking/api/transaction/user/7"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.invoicingParty").value("slotmachine-service"))
                .andExpect(jsonPath("$.amount").value(-2.00))
                .andRespond(withSuccess());

        client.createTransaction(7L, new BigDecimal("-2.00"));

        server.verify();
    }

    @Test
    void mapsBanking404ToDomainNotFound() {
        server.expect(requestTo("http://banking/casino/bank/api/user/404"))
                .andRespond(withResourceNotFound());

        assertThatThrownBy(() -> client.getUser(404L))
                .isInstanceOf(UserNotFoundException.class);
    }
}
