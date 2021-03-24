package model.builder;

import model.Account;
import model.Client;

public class ClientBuilder {

    private Client client;

    public ClientBuilder() {
        client=new Client();
    }

    public ClientBuilder setId(Long id) {
        client.setId(id);
        return this;
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setAccount(Account account) {
        client.setAccounts(account);
        return this;
    }


    public ClientBuilder setAddress(String address) {
        client.setAddress(address);
        return this;
    }

    public ClientBuilder setCardNb(String card_nb) {
        client.setCardNb(card_nb);
        return this;
    }

    public ClientBuilder setCnp(String cnp) {
        client.setCnp(cnp);
        return this;
    }

    public Client build() { return client; }
}
