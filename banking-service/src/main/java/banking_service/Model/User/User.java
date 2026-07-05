package banking_service.Model.User;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements IUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private BigDecimal balance;


    protected User() {}


    private User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = BigDecimal.ZERO;
    }


    public static IUser create(String firstName, String lastName) {
        validateName(firstName, "Vorname");
        validateName(lastName, "Nachname");
        return new User(firstName, lastName);
    }


    private static void validateName(String name, String field) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException(field + " darf nicht leer sein");
    }

    private static void validateBalance(BigDecimal balance) {
        if (balance == null || balance.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Kontostand darf nicht negativ sein");
    }


    @Override public Long getId() { return id; }
    @Override public String getFirstName() { return firstName; }
    @Override public String getLastName() { return lastName; }
    @Override public BigDecimal getBalance() { return balance; }


    public void setFirstName(String firstName) {
        validateName(firstName, "Vorname");
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        validateName(lastName, "Nachname");
        this.lastName = lastName;
    }

    public void setBalance(BigDecimal balance) {
        validateBalance(balance);
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", firstName='" + firstName +
                "', lastName='" + lastName + "', balance=" + balance + '}';
    }
}