package service.account;

import database.Bootstrapper;
import launcher.ComponentFactory;
import model.Account;
import model.Client;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.client.ClientService;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.*;

public class AccountServiceImplTest {

    public static ClientService clientService;
    public static AccountService accountService;
    public Account account;
    public Client client;

    @BeforeAll
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);

        clientService = componentFactory.getClientService();
        accountService = componentFactory.getAccountService();


    }

    @BeforeEach
    public void cleanUp()
    {
        clientService.removeAll();

        client = clientService.add("Iulia", "Cluj-Napoca", "1111111111", "2222222222222222").getResult();
        account = accountService.save("DEPOSIT", 100.0f, "1111111111", new Date(10)).getResult();
    }

    @Test
    public void findAll() {
        Assert.assertTrue(accountService.findAll().size() == 1);
    }

    @Test
    public void findById() {
        System.out.println(account.getId());
        Assert.assertFalse(accountService.findById(account.getId()).hasErrors());
    }

    @Test
    public void findByClientId() {
        Assert.assertFalse(accountService.findByClientId(client.getId()).hasErrors());
        Assert.assertFalse(accountService.findByClientId(account.getClient_id()).hasErrors());
    }

    @Test
    public void save() {
        Client client = clientService.findByCnp("1111111111").getResult();
        Account account = accountService.findByClientId(client.getId()).getResult();

        accountService.remove(account.getId());
        accountService.save("DEPOSIT", 150.0f, "1111111111", new Date(100));

        Assert.assertTrue(accountService.findAll().size() == 1);

    }

    @Test
    public void update() {
        Client client = clientService.findByCnp("1111111111").getResult();
        Account account = accountService.findByClientId(client.getId()).getResult();

        Assert.assertTrue(accountService.update(account.getId(), "Deposit", -1.0f, new Date(101)).hasErrors());
    }

    @Test
    public void transfer() {

        clientService.add("Bia", "Blaj", "1111111112", "2222222222222223");
        Account receiver = accountService.save("DEPOSIT", 100.0f, "1111111112", new Date(11)).getResult();

        Assert.assertTrue(accountService.transfer(receiver.getId(), account.getId(), 25).getResult());
        Assert.assertTrue(accountService.findById(account.getId()).getResult().getBalance() == 75);
        Assert.assertTrue(accountService.findById(receiver.getId()).getResult().getBalance() == 125);
    }

    @Test
    public void pay() {
        Assert.assertTrue(accountService.pay(account.getId(), 105).hasErrors());
        Assert.assertFalse(accountService.pay(account.getId(), 90).hasErrors());
        Assert.assertTrue(accountService.findById(account.getId()).getResult().getBalance() == 10);
    }

    @Test
    public void remove() {
        clientService.removeAll();
        Assert.assertTrue(accountService.findAll().size() == 0);
    }
}