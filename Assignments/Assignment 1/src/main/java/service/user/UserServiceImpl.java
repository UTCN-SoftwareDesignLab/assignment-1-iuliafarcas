package service.user;

import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.user.UserRepository;

import javax.xml.validation.Validator;
import java.util.List;

public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    public UserServiceImpl(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Notification<Boolean> save(String username, String password) {
        return authenticationService.register(username, password);
    }

    @Override
    public Notification<Boolean> update(Long id, String username, String password) {
        User user = new UserBuilder()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .build();

        UserValidator validator = new UserValidator(user);
        boolean valid = validator.validate();
        Notification<Boolean> notification = new Notification<>();

        if(valid)
        {
            user.setPassword(authenticationService.encodePassword(password));
            notification.setResult(userRepository.update(id, user));
        }
        else
        {
            validator.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
        }

        return notification;
    }

    @Override
    public boolean remove(Long id) {
        return userRepository.remove(id);
    }

    @Override
    public void removeAll() {
        userRepository.removeAll();
    }
}
