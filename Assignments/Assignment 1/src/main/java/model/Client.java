package model;

public class Client {

    private Long id;
    private String name;
    private String cnp;
    private String card_nb;
    private String address;
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardNb() {
        return card_nb;
    }

    public void setCardNb(String card_nb) {
        this.card_nb = card_nb;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccounts(Account account) {
        this.account = account;
    }
}
