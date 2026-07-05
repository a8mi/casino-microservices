package banking_service.Handler.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class UserHandlerConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void beanExists() {
        assertNotNull(applicationContext.getBean(UserHandler.class));
        assertNotNull(applicationContext.getBean(UserHandlerConfiguration.class));
    }

    @Test
    void onlyOneBean() {

        String[] beans = applicationContext.getBeanNamesForType(UserHandler.class);
        assertNotNull(beans);
    }
}