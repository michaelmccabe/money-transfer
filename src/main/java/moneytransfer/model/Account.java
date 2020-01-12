package moneytransfer.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Account{

    private final UUID uuid;
    private final UUID userUuid;
    private BigDecimal balance;

    public Account(UUID uuid, UUID userUuid, BigDecimal balance) {
        this.uuid = uuid;
        this.userUuid = userUuid;
        this.balance = balance;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
