package service.user;

import database.Bootstrapper;
import launcher.ComponentFactory;
import model.User;
import org.junit.Before;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.sql.SQLException;


public class AuthenticationServiceImplTest {

    public static final String TEST_USERNAME = "test@username.com";
    public static final String TEST_PASSWORD = "TestPassword1@";
    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;


    @BeforeAll
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);

        userRepository = componentFactory.getUserRepository();
        rightsRolesRepository = componentFactory.getRightsRolesRepository();
        authenticationService = componentFactory.getAuthenticationService();

    }

    @BeforeEach
    public void cleanUp()
    {
        userRepository.removeAll();
    }


    @Test
    public void register() {
        Assert.assertTrue(
                authenticationService.register(TEST_USERNAME, TEST_PASSWORD).getResult()
        );
    }

    @Test
    public void login() throws Exception{
        authenticationService.register(TEST_USERNAME, TEST_PASSWORD);
        User user = authenticationService.login(TEST_USERNAME, TEST_PASSWORD).getResult();
        Assert.assertNotNull(user);
    }
}