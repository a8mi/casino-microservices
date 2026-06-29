package roulette_service.Handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouletteHandlerConfiguration {
    @Bean
    IRouletteHandler rouletteHandler(){
        return new RouletteHandler();
    }
}
