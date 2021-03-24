package controller;

import model.Client;
import model.validation.Notification;
import service.client.ClientService;
import view.CreateUpdateClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateUpdateClientController {

    private final CreateUpdateClientView createUpdateClientView;
    private final ClientService clientService;

    public CreateUpdateClientController(CreateUpdateClientView createUpdateClientView, ClientService clientService) {
        this.createUpdateClientView = createUpdateClientView;
        this.clientService = clientService;

        createUpdateClientView.setCreateButtonListener(new AddButtonListener());
        createUpdateClientView.setUpdateButtonListener(new UpdateButtonListener());
    }

    private class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String username = createUpdateClientView.getTfFirstName() + " " +createUpdateClientView.getTfLastName();
            String address = createUpdateClientView.getTfAddress();
            String cnp = createUpdateClientView.getTfCNP();
            String card_nb = createUpdateClientView.getTfCardNb();

            Notification<Client> notification = clientService.add(username, address, cnp, card_nb);

            if (notification.hasErrors()) {
                JOptionPane.showMessageDialog(createUpdateClientView.getContentPane(), notification.getFormattedErrors());
            } else
                {
                    JOptionPane.showMessageDialog(createUpdateClientView.getContentPane(), "Registration successful!");
                }
            }
    }


    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String username = createUpdateClientView.getTfFirstName() + " " +createUpdateClientView.getTfLastName();
            String address = createUpdateClientView.getTfAddress();
            String cnp = createUpdateClientView.getTfCNP();
            String card_nb = createUpdateClientView.getTfCardNb();

            Long id = clientService.findByCnp(cnp).getResult().getId();
            Notification<Boolean> notification = clientService.update(id,username, address, cnp, card_nb);

            if (notification.hasErrors()) {
                JOptionPane.showMessageDialog(createUpdateClientView.getContentPane(), notification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(createUpdateClientView.getContentPane(), "Registration successful!");
            }
        }

    }
}


