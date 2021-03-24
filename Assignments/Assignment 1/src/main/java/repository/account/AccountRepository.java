package repository.account;

import model.Account;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountRepository {

    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;

    Notification<Account> save(Account account, Long clientId);

    Account findAccountByClientId(Long ownerId) throws  EntityNotFoundException;

    boolean update(Long id,Account account);

    boolean remove(Long id);

    void removeAll();
}
