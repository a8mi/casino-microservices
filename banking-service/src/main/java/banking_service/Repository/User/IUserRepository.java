package banking_service.Repository.User;

import banking_service.Model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {

}