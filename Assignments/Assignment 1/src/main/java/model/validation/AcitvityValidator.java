package model.validation;

import model.Activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AcitvityValidator {

    private final Activity activity;
    private final List<String> errors;

    public AcitvityValidator(Activity activity) {
        this.activity = activity;
        errors = new ArrayList<>();
    }


    private void validateDate(Date date)
    {
        if(date.after(new Date()))
            errors.add("Wrong date!");
    }

    public boolean validate()
    {
        validateDate(activity.getDate());
        return errors.isEmpty();
    }


    public List<String> getErrors() {
        return errors;
    }
}
