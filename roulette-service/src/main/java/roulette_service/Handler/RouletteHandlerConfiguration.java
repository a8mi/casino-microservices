package roulette_service.Handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import roulette_service.Client.IRouletteHTTPClient;
import roulette_service.Client.RouletteHTTPClient;
import roulette_service.Model.IRouletteGameFactory;
import roulette_service.Model.RouletteGameFactory;
import roulette_service.Repository.IRouletteGameRepository;

@Configuration
public class RouletteHandlerConfiguration {
    
    private final IRouletteGameRepository repository;

    public RouletteHandlerConfiguration(IRouletteGameRepository repository) {
        this.repository = repository;
    }

    @Bean
    public IRouletteGameFactory gameFactory() {
        return new RouletteGameFactory();
    }

    @Bean
    public IRouletteHTTPClient httpClient(){
        return new RouletteHTTPClient();
    }

    @Bean
    public IRouletteHandler rouletteHandler() {
        return new RouletteHandler(repository, gameFactory(), httpClient());
    }
}
