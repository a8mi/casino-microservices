package banking_service.Model.User;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

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

    // 1. PUBLIC Default-Konstruktor (für JPA + Tests)
    public User() {}

    // 2. Konstruktor mit allen Feldern (für Tests)
    public User(Long id, String firstName, String lastName, BigDecimal balance) {
        this.id = id;
        setFirstName(firstName);
        setLastName(lastName);
        setBalance(balance);
    }

    // 3. Konstruktor ohne ID (für neue User)
    public User(String firstName, String lastName) {
        this(null, firstName, lastName, BigDecimal.ZERO);
    }

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("Vorname darf nicht leer sein");
        this.firstName = firstName;
    }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("Nachname darf nicht leer sein");
        this.lastName = lastName;
    }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) {
        if (balance == null)
            throw new IllegalArgumentException("Balance darf nicht null sein");
        this.balance = balance;
    }

    // equals() + hashCode() (für Tests und Collections)
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

    // toString() (für Debugging)
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", balance=" + balance +
                '}';
    }
}