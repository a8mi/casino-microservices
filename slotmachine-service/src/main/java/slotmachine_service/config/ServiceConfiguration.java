package slotmachine_service.Config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.security.SecureRandom;
import java.time.Clock;
import java.util.random.RandomGenerator;

import slotmachine_service.Client.HTTPClient;
import slotmachine_service.GameLogic.PayoutPolicy;
import slotmachine_service.Handler.SlotMachineHandler;
import slotmachine_service.Model.ISlotGameFactory;
import slotmachine_service.Model.SlotGameFactory;
import slotmachine_service.GameLogic.SymbolGenerator;
import slotmachine_service.Repository.ISlotGameRepository;

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
    ISlotGameFactory slotGameFactory(){
        return new SlotGameFactory();
    }

    @Bean
    RandomGenerator slotRandomGenerator() {
        return new SecureRandom();
    }

    @Bean
    Clock clock(){
        return Clock.systemUTC();
    }

    @Bean
    SlotMachineHandler slotMachineHandler(
            ISlotGameRepository repository,
            ISlotGameFactory factory,
            HTTPClient bankingClient,
            SymbolGenerator symbolGenerator,
            PayoutPolicy payoutPolicy,
            Clock clock
    ) {
        return new SlotMachineHandler(repository, factory, bankingClient, symbolGenerator, payoutPolicy, clock) {
            
        };
    }
}
