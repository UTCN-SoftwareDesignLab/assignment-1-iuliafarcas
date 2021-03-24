package launcher;

import controller.*;
import database.DBConnectionFactory;
import model.User;
import model.builder.UserBuilder;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRoleRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.activity.ActivityService;
import service.activity.ActivityServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;
import view.*;

import java.sql.Connection;
import java.util.Collections;

public class ComponentFactory {

    private final LoginView loginView;
    private final AdminView adminView;
    private final EmployeeView employeeView;
    private final CreateUpdateEmployeeView createUpdateEmployeeView;
    private final CreateUpdateClientView createUpdateClientView;
    private final CreateUpdateAccountView createUpdateAccountView;

    private final LoginController loginController;
    private final AdminController adminController;
    private final EmployeeController employeeController;
    private final CreateUpdateEmployeeController createUpdateEmployeeController;
    private final CreateUpdateClientController createUpdateClientController;
    private final CreateUpdateAccountController createUpdateAccountController;

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final ClientService clientService;
    private final AccountService accountService;
    private final ActivityService activityService;

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ClientRepository clientRepository;
    private final ActivityRepository activityRepository;
    private final AccountRepository accountRepository;

    private static boolean ADMIN = false;

    private static ComponentFactory instance;

    private ComponentFactory(Boolean componentsForTests){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();

        this.rightsRolesRepository = new RightsRoleRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.accountRepository= new AccountRepositoryMySQL(connection);
        this.activityRepository = new ActivityRepositoryMySQL(connection);

        this.authenticationService=new AuthenticationServiceImpl(userRepository,rightsRolesRepository);
        this.userService=new UserServiceImpl(userRepository,authenticationService);
        this.clientService=new ClientServiceImpl(clientRepository);
        this.accountService=new AccountServiceImpl(accountRepository,clientService);
        this.activityService= new ActivityServiceImpl(activityRepository);

        this.loginView = new LoginView();
        this.adminView = new AdminView();
        this.employeeView = new EmployeeView();
        this.createUpdateEmployeeView = new CreateUpdateEmployeeView();
        this.createUpdateClientView = new CreateUpdateClientView();
        this.createUpdateAccountView = new CreateUpdateAccountView();

        this.loginController = new LoginController(loginView, authenticationService, adminView, employeeView);
        this.adminController = new AdminController(adminView, createUpdateEmployeeView);
        this.employeeController = new EmployeeController(employeeView, createUpdateClientView, createUpdateAccountView);
        this.createUpdateEmployeeController = new CreateUpdateEmployeeController(createUpdateEmployeeView, authenticationService, userService);
        this.createUpdateClientController = new CreateUpdateClientController(createUpdateClientView, clientService);
        this.createUpdateAccountController = new CreateUpdateAccountController(createUpdateAccountView, clientService, accountService);


        admin();

    }

    public static ComponentFactory instance(Boolean componentsForTests)
    {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    public void admin()
    {
        if(ADMIN)
        {
            User user = new UserBuilder()
                    .setUsername("admin@test.com")
                    .setPassword(authenticationService.encodePassword("Password1@"))
                    .setRoles(Collections.singletonList(rightsRolesRepository.findRoleByTitle("administrator")))
                    .build();
            userRepository.save(user);
        }
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository()
    {
        return this.rightsRolesRepository;
    }

    public AuthenticationService getAuthenticationService() {
        return this.authenticationService;
    }

    public UserService getUserService(){ return this.userService; }

    public ClientRepository getClientRepository(){ return  this.clientRepository; }

    public ClientService getClientService(){ return  this.clientService; }

    public ActivityRepository getActivityRepository() { return activityRepository; }

    public ActivityService getActivityService() { return activityService; }

    public AccountRepository getAccountRepository() { return accountRepository; }

    public AccountService getAccountService() { return accountService; }

    public LoginView getLoginView() { return loginView; }
}
