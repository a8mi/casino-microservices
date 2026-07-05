package banking_service.Model.User;

public interface IUserFactory {
    IUser create(String firstName, String lastName);
}