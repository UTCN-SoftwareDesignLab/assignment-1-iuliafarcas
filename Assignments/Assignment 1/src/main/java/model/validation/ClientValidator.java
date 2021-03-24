package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    private final Client client;
    private final List<String> errors;

    public ClientValidator(Client client) {
        this.client = client;
        errors = new ArrayList<>();
    }

    private void validateCardNb(String card_nb){
        if(card_nb.length() != 16)
        {
            errors.add("Card number must have 16 characters!");
        }

        if(!card_nb.matches("[0-9]+"))
        {
            errors.add("Card number must contain only digits");
        }
    }

    private void validateCnp(String cnp){
        if(cnp.length() != 10)
        {
            errors.add("CNP must have 10 characters!");
        }

        if(!cnp.matches("[0-9]+"))
        {
            errors.add("CNP must contain only digits");
        }
    }

    public boolean validate() {
        validateCardNb(client.getCardNb());
        validateCnp(client.getCnp());
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

}
