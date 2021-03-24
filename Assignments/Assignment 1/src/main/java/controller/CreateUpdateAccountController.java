package controller;

import model.Account;
import model.Client;
import model.validation.Notification;
import service.account.AccountService;
import service.client.ClientService;
import view.CreateUpdateAccountView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;

public class CreateUpdateAccountController {

    private final CreateUpdateAccountView createUpdateAccountView;
    private final ClientService clientService;
    private final AccountService accountService;

    public CreateUpdateAccountController(CreateUpdateAccountView createUpdateAccountView, ClientService clientService, AccountService accountService) {
        this.createUpdateAccountView = createUpdateAccountView;
        this.clientService = clientService;
        this.accountService = accountService;

        createUpdateAccountView.setCreateButtonListener(new AddButtonListener());
        createUpdateAccountView.setUpdateButtonListener(new UpdateButtonListener());
    }

    private class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String type = createUpdateAccountView.gettfType();
            String balance = createUpdateAccountView.getBalance();
            String cnp = createUpdateAccountView.getcnp();
            String date = createUpdateAccountView.getDate();

            Notification<Account> notification = accountService.save(type, Float.parseFloat(balance), cnp, new Date(Integer.parseInt(date)));

            if (notification.hasErrors()) {
                JOptionPane.showMessageDialog(createUpdateAccountView.getContentPane(), notification.getFormattedErrors());
            } else
            {
                JOptionPane.showMessageDialog(createUpdateAccountView.getContentPane(), "Registration successful!");
            }
        }
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String id = createUpdateAccountView.getID();
            String type = createUpdateAccountView.gettfType();
            String balance = createUpdateAccountView.getBalance();
            String date = createUpdateAccountView.getDate();

            Notification<Boolean> notification = accountService.update(Long.parseLong(id),type, Float.parseFloat(balance), new Date(Integer.parseInt(date)));

            if (notification.hasErrors()) {
                JOptionPane.showMessageDialog(createUpdateAccountView.getContentPane(), notification.getFormattedErrors());
            } else
            {
                JOptionPane.showMessageDialog(createUpdateAccountView.getContentPane(), "Registration successful!");
            }
        }
    }
}
