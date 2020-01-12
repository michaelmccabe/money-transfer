package moneytransfer.model;

public enum Type {

    DEPOSIT("Deposit to account"),
    WITHDRAWAL("Withdrawal from account"),
    TRANSFER("Account transfer");

    private final String description;
    Type(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
