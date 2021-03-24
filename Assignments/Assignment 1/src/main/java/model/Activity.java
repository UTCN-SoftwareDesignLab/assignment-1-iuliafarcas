package model;

import java.util.Date;

public class Activity {

    private Long id;
    private Long employeeId;
    private Date date;
    private String type;
    private Long client_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getClientId(){ return this.client_id; }

    public void setClientId(Long client_id) { this.client_id = client_id; }
}
