package model.validation;

import com.sun.jdi.request.BreakpointRequest;
import model.Account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountValidator {

    private final Account account;
    private final List<String> errors;


    public AccountValidator(Account account) {
        this.account = account;
        this.errors = new ArrayList<>();
    }

    private void validateCreation(Date creation){
        if(creation.after(new Date()))
        {
            errors.add("Wrong date!");
        }
    }

    private void validateBalance(float balance) {
        if(balance < 0)
        {
            errors.add("Can't open an account with negative balance!");
        }
    }

    public boolean validate(){
        validateBalance(account.getBalance());
        validateCreation(account.getCreation());
        return errors.isEmpty();
    }

    public List<String> getErrors(){
        return errors;
    }
}
