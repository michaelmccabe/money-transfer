package moneytransfer.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class TransactionJson {
    private String accountId;
    private String destinationAccountId;
    private String details;
    private BigDecimal amount;

    public TransactionJson(String accountId, String details, BigDecimal amount, String destinationAccountId) {
        this.accountId = accountId;
        this.destinationAccountId = destinationAccountId;
        this.details = details;
        this.amount = amount;
    }

    public String getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(String destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
