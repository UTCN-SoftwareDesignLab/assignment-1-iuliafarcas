package model;

import java.util.Date;

public class Account {

    private Long id;
    private Date creation;
    private String type;
    private float balance;
    private Long client_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Long getClient_id() { return client_id; }

    public void setClient_id(Long client_id) { this.client_id = client_id; }
}
