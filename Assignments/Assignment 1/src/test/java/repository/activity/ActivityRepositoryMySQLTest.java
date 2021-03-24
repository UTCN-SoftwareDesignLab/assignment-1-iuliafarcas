package repository.activity;



import launcher.ComponentFactory;
import model.Activity;
import model.builder.ActivityBuilder;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.EntityNotFoundException;

import java.util.Date;

import static org.junit.Assert.*;

public class ActivityRepositoryMySQLTest {

    private static ActivityRepository activityRepository;
    private static Activity activity;

    @BeforeAll
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);

        activityRepository = componentFactory.getActivityRepository();

    }

    @BeforeEach
    public void cleanUp()
    {
        activityRepository.removeAll();
        activity = new ActivityBuilder()
                .setType("Transfer")
                .setClientId(100L)
                .setPerformer(5L)
                .setDate(new Date(70))
                .build();

        activityRepository.save(activity);
    }

    @Test
    public void findAll() {
        Assert.assertTrue(activityRepository.findAll().size() == 1);
    }

    @Test
    public void findById() throws EntityNotFoundException {

        Assert.assertTrue(activityRepository.findById(activity.getId()).getClientId().equals(100L));
    }

    @Test
    public void findByEmployeeId() {
        Assert.assertTrue(activityRepository.findByEmployeeId(5L).size() == 1);
    }

    @Test
    public void save() {
        Activity activity1 = new ActivityBuilder()
                .setType("Payment")
                .setClientId(100L)
                .setPerformer(5L)
                .setDate(new Date(70))
                .build();

        activityRepository.save(activity1);
        Assert.assertTrue(activityRepository.findByEmployeeId(5L).size() == 2);
    }
}