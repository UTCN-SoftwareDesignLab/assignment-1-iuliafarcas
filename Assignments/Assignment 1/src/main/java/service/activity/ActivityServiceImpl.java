package service.activity;

import model.Activity;
import model.builder.ActivityBuilder;
import model.validation.AcitvityValidator;
import model.validation.Notification;
import repository.activity.ActivityRepository;

import javax.swing.*;
import java.util.Date;
import java.util.List;

public class ActivityServiceImpl implements ActivityService{

    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    @Override
    public Notification<Boolean> save(String type, Long user_id, Date date, Long client_id) {
        Activity activity = new ActivityBuilder()
                .setType(type)
                .setPerformer(user_id)
                .setDate(date)
                .setClientId(client_id)
                .build();

        AcitvityValidator validator = new AcitvityValidator(activity);
        boolean valid = validator.validate();
        Notification<Boolean> notification = new Notification<>();

        if(valid)
        {
            notification.setResult(activityRepository.save(activity));
        }
        else
        {
            validator.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
        }

        return notification;
    }

    @Override
    public List<Activity> findByUser(Long user_id) {
        return activityRepository.findByEmployeeId(user_id);
    }

    @Override
    public void removeAll() {
        activityRepository.removeAll();
    }
}
