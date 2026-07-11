package banking_service.Handler.Transactions;

import banking_service.Model.Transactions.ITransactionFactory;
import banking_service.Repository.Transactions.TransactionRepository;
import banking_service.Repository.User.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionHandlerConfiguration {

    @Bean
    public ITransactionHandler transactionHandler(
            TransactionRepository transactionRepository,
            IUserRepository userRepository,
            ITransactionFactory transactionFactory) {
        return new TransactionHandler(transactionRepository, userRepository, transactionFactory);
    }
}