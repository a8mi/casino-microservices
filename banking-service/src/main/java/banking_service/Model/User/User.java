package banking_service.Model.User;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "users")
public class User {

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

    public User(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("Vorname darf nicht leer sein");
        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("Nachname darf nicht leer sein");
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = BigDecimal.ZERO;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public BigDecimal getBalance() { return balance; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}