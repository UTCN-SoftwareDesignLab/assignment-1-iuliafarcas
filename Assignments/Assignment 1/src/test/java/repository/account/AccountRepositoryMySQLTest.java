package repository.account;

import launcher.ComponentFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;

import java.util.Date;

import static org.junit.Assert.*;
import static service.account.AccountServiceImplTest.accountService;

public class AccountRepositoryMySQLTest {

    public static AccountRepository accountRepository;
    public static ClientRepository clientRepository;
    public static Client client;
    public static Account account;

    @BeforeAll
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);

        clientRepository= componentFactory.getClientRepository();
        accountRepository = componentFactory.getAccountRepository();


    }

    @BeforeEach
    public void cleanUp()
    {
        clientRepository.removeAll();

        client = new ClientBuilder()
                .setName("Iulia")
                .setAddress("Cluj-Napoca")
                .setCnp("1111111111")
                .setCardNb("2222222222222222")
                .build();
        clientRepository.save(client).getResult();

        account = new AccountBuilder()
                .setType("DEPOSIT")
                .setBalance(100.0f)
                .setClientId(client.getId())
                .setCreation(new Date(10))
                .build();
        accountRepository.save(account, client.getId()).getResult();
    }

    @Test
    public void findAll() {
        Assert.assertTrue(accountRepository.findAll().size() == 1);
    }

    @Test
    public void findById() throws EntityNotFoundException {
        Assert.assertTrue(accountRepository.findById(account.getId()).getClient_id().equals(client.getId()));
    }

    @Test
    public void save() {
        Client client1 = new ClientBuilder()
                .setName("Iulia Maria")
                .setAddress("Cluj-Napoca")
                .setCnp("1111111112")
                .setCardNb("2222222222222223")
                .build();
        clientRepository.save(client1).getResult();

        Account account1 = new AccountBuilder()
                .setType("DEPOSIT")
                .setBalance(100.0f)
                .setClientId(client1.getId())
                .setCreation(new Date(10))
                .build();
        accountRepository.save(account1, client1.getId()).getResult();

        Assert.assertTrue(accountRepository.findAll().size() == 2);
    }

    @Test
    public void findAccountByClientId() throws EntityNotFoundException {
        Assert.assertTrue(accountRepository.findAccountByClientId(account.getClient_id()).getId().equals(account.getId()));
    }

    @Test
    public void update() throws EntityNotFoundException {
        Account account1 = new AccountBuilder()
                .setType("DEPOSIT")
                .setBalance(200.0f)
                .setClientId(client.getId())
                .setCreation(new Date(10))
                .build();

        Assert.assertTrue(accountRepository.update(account.getId(), account1));
        Assert.assertTrue(accountRepository.findAccountByClientId(client.getId()).getBalance() == 200);

    }

    @Test
    public void remove() {
        //clientRepository.remove(client.getId());
        accountRepository.remove(account.getId());
        Assert.assertTrue(accountRepository.findAll().size() == 0);
    }
}