package model.builder;

import model.Activity;

import java.util.Date;

public class ActivityBuilder {

    private Activity activity;

    public ActivityBuilder() {
        activity=new Activity();
    }

    public ActivityBuilder setId(Long id) {
        activity.setId(id);
        return this;
    }

    public ActivityBuilder setPerformer(Long performerId){
        activity.setEmployeeId(performerId);
        return this;
    }

    public ActivityBuilder setDate(Date date) {
        activity.setDate(date);
        return this;
    }

    public ActivityBuilder setType(String type) {
        activity.setType(type);
        return this;
    }

    public Activity build()
    {
        return  activity;
    }

    public ActivityBuilder setClientId(Long client_id) {
        activity.setClientId(client_id);
        return this;
    }
}
