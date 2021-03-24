package service.account;

import model.Account;
import model.validation.Notification;

import java.util.Date;
import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Notification<Account> findById(Long id);

    Notification<Account> findByClientId(Long client_id);

    Notification<Account> save(String type,Float balance,String cnp, Date creation);

    Notification<Boolean> update(Long id, String type, Float balance, Date creation);

    Notification<Boolean> transfer(Long receiverId,Long senderId, int sum);

    Notification<Boolean> pay(Long payerId,int sum);

    boolean remove(Long id);
}
