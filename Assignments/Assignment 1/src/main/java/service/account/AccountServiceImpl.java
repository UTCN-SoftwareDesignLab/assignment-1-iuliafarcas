package service.account;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.validation.AccountValidator;
import model.validation.Notification;
import model.validation.OperationsValidator;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;
import service.client.ClientService;

import java.util.Date;
import java.util.List;

public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final ClientService clientService;

    public AccountServiceImpl(AccountRepository accountRepository, ClientService clientService) {
        this.accountRepository = accountRepository;
        this.clientService = clientService;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Notification<Account> findById(Long id) {
        /*try{
            return accountRepository.findById(id);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }*/

        Notification<Account> notification = new Notification<>();
        try{
            notification.setResult(accountRepository.findById(id));
            return notification;
        } catch (EntityNotFoundException e) {
            notification.addError(e.getMessage());
            return notification;
        }
    }

    @Override
    public Notification<Account> findByClientId(Long client_id) {
        /*try{
            return accountRepository.findAccountByClientId(client_id);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }*/

        Notification<Account> notification = new Notification<>();
        try{
            notification.setResult(accountRepository.findAccountByClientId(client_id));
            return notification;
        } catch (EntityNotFoundException e) {
            notification.addError(e.getMessage());
            return notification;
        }
    }

    @Override
    public Notification<Account> save(String type, Float balance, String cnp, Date creation) {
        Account account = new AccountBuilder()
                .setType(type)
                .setCreation(creation)
                .setBalance(balance)
                .build();

        AccountValidator validator = new AccountValidator(account);
        Notification<Account> notification = new Notification<>();
        Notification<Client> findClient = clientService.findByCnp(cnp);

        if(findClient.hasErrors())
        {
            validator.getErrors().forEach(notification::addError);
            return notification;
        }

        boolean valid = validator.validate();

        if(valid)
        {
            Long client_id = findClient.getResult().getId();
            account.setClient_id(client_id);
            notification.setResult(accountRepository.save(account, client_id).getResult());
        }
        else
        {
            validator.getErrors().forEach(notification::addError);
        }

        return notification;
    }

    @Override
    public Notification<Boolean> update(Long id, String type, Float balance, Date creation) {
        Account account = new AccountBuilder()
                .setType(type)
                .setCreation(creation)
                .setBalance(balance)
                .build();

        AccountValidator validator = new AccountValidator(account);
        Notification<Boolean> notification = new Notification<>();

        boolean valid = validator.validate();

        if(valid)
        {
            notification.setResult(accountRepository.update(id, account));
        }
        else
        {
            validator.getErrors().forEach(notification::addError);
            notification.setResult(false);
        }

        return notification;
    }

    @Override
    public Notification<Boolean> transfer(Long receiverId, Long senderId, int sum) {

        Account sender = findById(senderId).getResult();
        Account receiver = findById(receiverId).getResult();

        OperationsValidator validator = new OperationsValidator(sender, sum);
        Notification<Boolean> notification = new Notification<>();

        boolean valid = validator.validate();

        if(valid)
        {
            boolean first = update(receiver.getId(), receiver.getType(), receiver.getBalance() + sum, receiver.getCreation()).getResult();
            boolean second = update(sender.getId(), sender.getType(), sender.getBalance() - sum, sender.getCreation()).getResult();
            notification.setResult(first & second);
        }
        else {
            validator.getErrors().forEach(notification::addError);
            notification.setResult(false);
        }

        return  notification;
    }

    @Override
    public Notification<Boolean> pay(Long payerId, int sum) {
        Account account = findById(payerId).getResult();

        OperationsValidator validator = new OperationsValidator(account, sum);
        Notification<Boolean> notification = new Notification<>();

        boolean valid = validator.validate();

        if(valid)
        {
            notification.setResult(update(account.getId(), account.getType(), account.getBalance() - sum, account.getCreation()).getResult());
        }
        else {
            validator.getErrors().forEach(notification::addError);
            notification.setResult(false);
        }

        return  notification;
    }

    @Override
    public boolean remove(Long id) {
        return accountRepository.remove(id);
    }

    public void removeAll()
    {
        accountRepository.removeAll();
    }
}
