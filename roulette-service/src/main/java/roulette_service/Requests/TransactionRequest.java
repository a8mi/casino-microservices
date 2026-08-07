package roulette_service.Requests;

import java.math.BigDecimal;

public record TransactionRequest(String invoicingParty, BigDecimal amount) implements ITransactionRequest {

    public static TransactionRequest create (BigDecimal amount){
        return new TransactionRequest("roulette-service", amount);
    }
    
    @Override
    public BigDecimal getAmount() {return this.amount;}

    @Override
    public String getInvoicingParty() {return this.invoicingParty;}
    
}
