package banking_service.Handler.User;

import banking_service.Model.User.IUserFactory;
import banking_service.Model.User.UserFactory;
import banking_service.Repository.User.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserHandlerConfiguration {

    private final IUserRepository userRepository;

    public UserHandlerConfiguration(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public IUserFactory userFactory() {
        return new UserFactory();
    }

    @Bean
    public IUserHandler userHandler() {
        return new UserHandler(userRepository, userFactory());
    }
}