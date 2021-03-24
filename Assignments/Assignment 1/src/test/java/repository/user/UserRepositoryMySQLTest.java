package repository.user;

import launcher.ComponentFactory;
import model.User;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.EntityNotFoundException;
import repository.security.RightsRolesRepository;
import service.user.AuthenticationService;
import service.user.UserService;

import java.util.Collections;

import static org.junit.Assert.*;

public class UserRepositoryMySQLTest {

    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;
    private static User user;

    @BeforeAll
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);

        userRepository = componentFactory.getUserRepository();
        rightsRolesRepository = componentFactory.getRightsRolesRepository();

    }

    @BeforeEach
    public void cleanUp()
    {
        userRepository.removeAll();

        userRepository.save(new UserBuilder()
                .setUsername("admin@test.com")
                .setPassword("Admin123$")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle("administrator")))
                .build());
    }


    @Test
    public void findById() throws EntityNotFoundException {

        User user = new UserBuilder()
                .setUsername("admin1@test.com")
                .setPassword("Admin123$")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle("administrator")))
                .build();
        userRepository.save(user);

        Assert.assertTrue(userRepository.findById(user.getId()).getUsername().matches("admin1@test.com"));
    }

    @Test
    public void findAll() {
        Assert.assertTrue(userRepository.findAll().size() == 1);
    }

    @Test
    public void save() {
        User user = new UserBuilder()
                .setUsername("admin1@test.com")
                .setPassword("Admin123$")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle("administrator")))
                .build();
        Assert.assertTrue(userRepository.save(user));
    }

    @Test
    public void update() {

        User user = new UserBuilder()
                .setUsername("admin1@test.com")
                .setPassword("Admin123$")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle("administrator")))
                .build();
        userRepository.save(user);

        User user1 = new UserBuilder()
                .setUsername("admin1@test.com")
                .setPassword("Admin")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle("administrator")))
                .build();

        Assert.assertTrue(userRepository.update(user.getId(), user1));
    }

    @Test
    public void remove() {
        User user = new UserBuilder()
                .setUsername("admin1@test.com")
                .setPassword("Admin123$")
                .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle("administrator")))
                .build();
        userRepository.save(user);
        Assert.assertTrue(userRepository.remove(user.getId()));
    }
}