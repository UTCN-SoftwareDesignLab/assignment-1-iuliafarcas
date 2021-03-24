package repository.user;

import model.User;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

public interface UserRepository {

    Notification<User> findByUsernameAndPassword(String username, String password);

    User findById(Long id) throws EntityNotFoundException;

    List<User> findAll();

    boolean save(User user);

    boolean update(Long id,User user);

    boolean remove(Long id);

    void removeAll();
}
