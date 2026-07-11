package banking_service.Model.Transactions;

import banking_service.Utils.ErrorMessages;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction implements ITransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "invoicing_party", nullable = false)
    private String invoicingParty;

    @Column(nullable = false)
    private BigDecimal amount;

    protected Transaction() {}

    private Transaction(Long userId, String invoicingParty, BigDecimal amount) {
        this.userId = userId;
        this.invoicingParty = invoicingParty;
        this.amount = amount;
    }

    public static ITransaction create(Long userId, String invoicingParty, BigDecimal amount) {
        validate(userId, invoicingParty, amount);
        return new Transaction(userId, invoicingParty, amount);
    }

    private static void validate(Long userId, String invoicingParty, BigDecimal amount) {
        if (userId == null) {
            throw new IllegalArgumentException(ErrorMessages.USER_ID_NULL);
        }
        if (invoicingParty == null || invoicingParty.isBlank()) {
            throw new IllegalArgumentException(ErrorMessages.INVOICING_PARTY_BLANK);
        }
        if (amount == null) {
            throw new IllegalArgumentException(ErrorMessages.AMOUNT_NULL);
        }
    }

    @Override public Long getId() { return id; }
    @Override public Long getUserId() { return userId; }
    @Override public String getInvoicingParty() { return invoicingParty; }
    @Override public BigDecimal getAmount() { return amount; }

    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) {
        if (userId == null) throw new IllegalArgumentException(ErrorMessages.USER_ID_NULL);
        this.userId = userId;
    }

    public void setInvoicingParty(String invoicingParty) {
        if (invoicingParty == null || invoicingParty.isBlank())
            throw new IllegalArgumentException(ErrorMessages.INVOICING_PARTY_BLANK);
        this.invoicingParty = invoicingParty;
    }

    public void setAmount(BigDecimal amount) {
        if (amount == null) throw new IllegalArgumentException(ErrorMessages.AMOUNT_NULL);
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Transaction{id=" + id + ", userId=" + userId +
                ", invoicingParty='" + invoicingParty + "', amount=" + amount + '}';
    }
}