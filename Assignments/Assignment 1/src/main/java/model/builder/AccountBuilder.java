package model.builder;

import model.Account;

import java.util.Date;

public class AccountBuilder {

    private Account account;

    public AccountBuilder() {
        account=new Account();
    }

    public AccountBuilder setId(Long id) {
        account.setId(id);
        return this;
    }

    public AccountBuilder setType(String type) {
        account.setType(type);
        return this;
    }

    public AccountBuilder setBalance(float balance) {
        account.setBalance(balance);
        return this;
    }

    public AccountBuilder setCreation(Date date) {
        account.setCreation(date);
        return this;
    }

    public AccountBuilder setClientId(Long clientId)
    {
        account.setClient_id(clientId);
        return this;
    }

    public Account build() { return account; }
}
