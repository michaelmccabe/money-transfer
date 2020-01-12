package moneytransfer.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class Transaction {
    private UUID  uuid;
    private Type type;
    private UUID sourceAccountUuid;
    private UUID accountUuid;
    private String details;
    private BigDecimal amount;
    private long timeStamp;

    public Transaction(UUID uuid, Type type, UUID sourceAccountUuid, UUID accountUuid, String details, BigDecimal amount, long timeStamp) {
        this.uuid = uuid;
        this.type = type;
        this.sourceAccountUuid = sourceAccountUuid;
        this.accountUuid = accountUuid;
        this.details = details;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    public UUID getSourceAccountUuid() {
        return sourceAccountUuid;
    }

    public void setSourceAccountUuid(UUID sourceAccountUuid) {
        this.sourceAccountUuid = sourceAccountUuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public UUID getAccountUuid() {
        return accountUuid;
    }

    public void setAccountUuid(UUID accountUuid) {
        this.accountUuid = accountUuid;
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
