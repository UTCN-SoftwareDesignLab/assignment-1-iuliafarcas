package service.client;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;

import javax.xml.validation.Validator;
import java.util.List;

public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findall() {
        return clientRepository.findAll();
    }

    @Override
    public Notification<Client> add(String name, String address, String cnp, String card_nb) {
        Client client = new ClientBuilder()
                .setName(name)
                .setAddress(address)
                .setCnp(cnp)
                .setCardNb(card_nb)
                .build();

        ClientValidator validator = new ClientValidator(client);
        boolean valid = validator.validate();
        Notification<Client> notification = new Notification<>();

        if(valid)
        {
            notification.setResult(clientRepository.save(client).getResult());
        }
        else
        {
            validator.getErrors().forEach(notification::addError);
        }

        return notification;
    }

    @Override
    public Notification<Boolean> update(Long id, String name, String address, String cnp, String card_nb) {
        Client client = new ClientBuilder()
                .setName(name)
                .setAddress(address)
                .setCnp(cnp)
                .setCardNb(card_nb)
                .build();

        ClientValidator validator = new ClientValidator(client);
        boolean valid = validator.validate();
        Notification<Boolean> notification = new Notification<>();

        if(valid)
        {
            notification.setResult(clientRepository.update(id, client));
        }
        else
        {
            validator.getErrors().forEach(notification::addError);
            notification.setResult(false);
        }

        return notification;
    }

    @Override
    public Notification<Client> findById(Long id) {
        /*try{
            return clientRepository.findById(id);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }*/

        Notification<Client> notification = new Notification<>();

        try{
            notification.setResult(clientRepository.findById(id));
        } catch (EntityNotFoundException e) {
            notification.addError("Couldn't find client with this CNP");
        }

        return notification;


    }

    @Override
    public Notification<Client> findByCnp(String cnp) {
        /*try{
            return clientRepository.findByCnp(cnp);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }*/

        Notification<Client> notification = new Notification<>();

        try{
            notification.setResult(clientRepository.findByCnp(cnp));
        } catch (EntityNotFoundException e) {
            notification.addError("Couldn't find client with this CNP");
        }

        return notification;
    }

    @Override
    public boolean remove(Long id) {
        return clientRepository.remove(id);
    }

    @Override
    public void removeAll() {
        clientRepository.removeAll();
    }
}
