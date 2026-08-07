package roulette_service.Requests;

import java.math.BigDecimal;

public interface ITransactionRequest {
    BigDecimal getAmount();
    String getInvoicingParty();
}