package service.activity;

import launcher.ComponentFactory;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ActivityServiceImplTest {

    public static ActivityService activityService;

    @BeforeAll
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);

        activityService = componentFactory.getActivityService();
    }

    @BeforeEach
    public void cleanUp()
    {
        activityService.removeAll();

        activityService.save("NEW CLIENT", 1L, new Date(111), 11L);
    }

    @Test
    public void findAll() {
        Assert.assertTrue(activityService.findAll().size() == 1);
    }

    @Test
    public void save() {
        Assert.assertFalse(activityService.save("TRANSFER", 1L, new Date(111), 12L).hasErrors());
    }

    @Test
    public void findByUser() {
        Assert.assertFalse(activityService.findByUser(1L).size() == 3);

        activityService.save("NEW CLIENT", 1L, new Date(111), 12L);
        activityService.save("TRANSFER", 1L, new Date(111), 12L);

        Assert.assertTrue(activityService.findByUser(1L).size() == 3);
    }
}