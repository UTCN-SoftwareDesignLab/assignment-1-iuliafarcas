package service.client;

import launcher.ComponentFactory;
import model.Client;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepository;
import repository.user.UserRepository;

import static org.junit.Assert.*;

public class ClientServiceImplTest {

    private static ClientRepository clientRepository;
    private static ClientService clientService;

    @BeforeAll
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);

        clientRepository = componentFactory.getClientRepository();
        clientService = componentFactory.getClientService();

    }

    @BeforeEach
    public void cleanUp()
    {
        clientService.removeAll();

        clientService.add("Iulia", "Cluj-Napoca", "1111111111", "2222222222222222");
    }

    @Test
    public void findall() {
        Assert.assertTrue(clientService.findall().size() == 1);
    }

    @Test
    public void add() {
        boolean valid = clientService.add("Roxana", "Fargaras", "2222222222", "3").hasErrors();
        Assert.assertTrue(valid);

        valid = clientService.add("Roxana", "Fargaras", "2222222222", "3333333333333333").hasErrors();
        Assert.assertFalse(valid);
    }

    @Test
    public void update() {
        Client client = clientService.findByCnp("1111111111").getResult();

        Assert.assertTrue(clientService.update(client.getId(),"Iulia Maria", "Cluj-Napoca", "1111111111", "2222222222222222").getResult());
    }

    @Test
    public void findById() {
        Client client = clientService.findByCnp("1111111111").getResult();
        Assert.assertFalse(clientService.findById(client.getId()).hasErrors());
    }

    @Test
    public void findByCnp() {
        boolean valid = clientService.findByCnp("1111111111").hasErrors();
        Assert.assertFalse(valid);
    }

    @Test
    public void remove() {
        Client client = clientService.findByCnp("1111111111").getResult();
        Assert.assertTrue(clientService.remove(client.getId()));
    }
}