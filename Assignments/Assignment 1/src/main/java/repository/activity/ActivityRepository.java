package repository.activity;

import model.Activity;
import repository.EntityNotFoundException;

import java.util.List;

public interface ActivityRepository {

    List<Activity> findAll();

    //search an activity by its id
    Activity findById(Long id) throws EntityNotFoundException;

    List<Activity> findByEmployeeId(Long userId);

    boolean save(Activity activity);

    void removeAll();
}
