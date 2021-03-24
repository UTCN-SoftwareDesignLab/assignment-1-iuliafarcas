package repository.client;

import launcher.ComponentFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.EntityNotFoundException;

import static org.junit.Assert.*;

public class ClientRepositoryMySQLTest {

    private static ClientRepository clientRepository;
    private Client client;

    @BeforeAll
    public static void setUp() {
        ComponentFactory componentFactory = ComponentFactory.instance(true);

        clientRepository = componentFactory.getClientRepository();

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
    }

    @Test
    public void findAll() {
        Assert.assertTrue(clientRepository.findAll().size() == 1);
    }

    @Test
    public void findById() throws EntityNotFoundException {
        Assert.assertTrue(clientRepository.findById(client.getId()).getCnp().equals(client.getCnp()));
    }

    @Test
    public void findByCnp() throws EntityNotFoundException {
        Assert.assertTrue(clientRepository.findByCnp(client.getCnp()).getId().equals(client.getId()));
    }

    @Test
    public void save() {
        Client client1 = new ClientBuilder()
                .setName("Iulia Maria")
                .setAddress("Cluj-Napoca")
                .setCnp("1111111112")
                .setCardNb("2222222222222223")
                .build();

        Assert.assertFalse(clientRepository.save(client1).hasErrors());
    }

    @Test
    public void update() throws EntityNotFoundException {

        Client updateClient = new ClientBuilder()
                .setName("Iulia Maria")
                .setAddress("Cluj-Napoca")
                .setCnp("1111111111")
                .setCardNb("2222222222222222")
                .build();

        clientRepository.update(client.getId(), updateClient);

        Assert.assertTrue(clientRepository.findById(client.getId()).getName().equals(updateClient.getName()));

    }

    @Test
    public void remove() {
        clientRepository.remove(client.getId());
        Assert.assertTrue(clientRepository.findAll().size() == 0);
    }
}