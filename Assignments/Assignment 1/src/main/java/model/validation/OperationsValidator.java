package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.List;

public class OperationsValidator {

    private final Account account;
    private final float sum;
    private final List<String> errors;

    public OperationsValidator(Account account, float sum) {
        this.account = account;
        this.sum = sum;
        this.errors = new ArrayList<>();
    }

    private void validateSum()
    {
        if(sum <= 0)
            errors.add("The sum must be a positive number!");
    }

    private void validateBalance()
    {
        if(account.getBalance() - sum <0)
            errors.add("Insufficient funds!");
    }

    public boolean validate()
    {
        validateBalance();
        validateSum();
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }
}
