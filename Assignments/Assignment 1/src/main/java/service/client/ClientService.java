package service.client;

import model.Client;
import model.validation.Notification;

import java.util.List;

public interface ClientService {

    List<Client> findall();

    Notification<Client> add(String name, String address, String cnp, String card_nb);

    Notification<Boolean> update(Long id, String name, String address, String cnp, String card_nb);

    Notification<Client> findById(Long id);

    Notification<Client> findByCnp(String cnp);

    boolean remove(Long id);

    void removeAll();
}
