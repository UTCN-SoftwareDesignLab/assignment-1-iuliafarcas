package service.activity;

import model.Activity;
import model.validation.Notification;

import java.util.Date;
import java.util.List;

public interface ActivityService {

    List<Activity> findAll();

    Notification<Boolean> save(String type, Long user_id, Date date, Long client_id);

    List<Activity> findByUser(Long user_id);

    void removeAll();
}
