package slotmachine_service.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.security.SecureRandom;
import java.util.random.RandomGenerator;
import slotmachine_service.client.BankingClient;
import slotmachine_service.client.HttpBankingClient;
import slotmachine_service.game.PayoutPolicy;
import slotmachine_service.game.SymbolGenerator;
import slotmachine_service.game.WeightedSymbolGenerator;
import slotmachine_service.repository.SlotGameRepository;
import slotmachine_service.service.SlotGameMapper;
import slotmachine_service.service.SlotMachineService;

@Configuration
@EnableConfigurationProperties(BankingProperties.class)
public class ServiceConfiguration {

    @Bean
    RestClient bankingRestClient(RestClient.Builder builder, BankingProperties properties) {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(properties.connectTimeout())
                .build();

        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);
        requestFactory.setReadTimeout(properties.readTimeout());

        return builder
                .baseUrl(properties.baseUrl().toString())
                .requestFactory(requestFactory)
                .build();
    }

    @Bean
    RandomGenerator slotRandomGenerator() {
        return new SecureRandom();
    }

    @Bean
    SlotMachineService slotMachineService(
            SlotGameRepository repository,
            BankingClient bankingClient,
            SymbolGenerator symbolGenerator,
            PayoutPolicy payoutPolicy,
            SlotGameMapper mapper
    ) {
        return new SlotMachineService(repository, bankingClient, symbolGenerator, payoutPolicy, mapper);
    }
}
