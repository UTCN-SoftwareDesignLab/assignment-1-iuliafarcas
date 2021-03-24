package service.user;

import launcher.ComponentFactory;
//import org.junit.Test;
import model.User;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceImplTest {

    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;
    private static UserService userService;

    @BeforeAll
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);

        userRepository = componentFactory.getUserRepository();
        rightsRolesRepository = componentFactory.getRightsRolesRepository();
        authenticationService = componentFactory.getAuthenticationService();
        userService = componentFactory.getUserService();

    }

    @BeforeEach
    public void cleanUp()
    {
        //userRepository.removeAll();

        userService.removeAll();

        userService.save("admin@test.com", "Admin123$");
        userService.save("user@test.com", "User1234%");

    }

    @Test
    public void findAll() {
        Assert.assertTrue(userService.findAll().size() == 2);
    }

    @Test
    public void save() {
        Assert.assertTrue(userService.save("user1@test.com", "1@").hasErrors());
        Assert.assertTrue(userService.save("user2@test.com", "parolaUser2!").getResult());
    }

    @Test
    public void update() {
        User user = userRepository.findByUsernameAndPassword("user@test.com",authenticationService.encodePassword("User1234%")).getResult();

        Assert.assertTrue(userService.update(user.getId(), "user@test.com", "NewPass1!").getResult());
    }

    @Test
    public void remove() {
        User user = userRepository.findByUsernameAndPassword("user@test.com",authenticationService.encodePassword("User1234%")).getResult();
        userService.remove(user.getId());
        Assert.assertTrue(userService.findAll().size() == 1);
    }
}