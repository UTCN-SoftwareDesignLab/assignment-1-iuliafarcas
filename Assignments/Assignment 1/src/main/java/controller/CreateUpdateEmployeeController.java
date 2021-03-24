package controller;

import model.validation.Notification;
import service.user.AuthenticationService;
import service.user.UserService;
import view.CreateUpdateEmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateUpdateEmployeeController {

    private final CreateUpdateEmployeeView createUpdateEmployeeView;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public CreateUpdateEmployeeController(CreateUpdateEmployeeView createUpdateEmployeeView, AuthenticationService authenticationService, UserService userService) {
        this.createUpdateEmployeeView = createUpdateEmployeeView;
        this.authenticationService = authenticationService;
        this.userService = userService;

        createUpdateEmployeeView.setCreateButtonListener(new AddButtonListener());
        createUpdateEmployeeView.setUpdateButtonListener(new UpdateButtonListener());
    }

    private class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String username = createUpdateEmployeeView.getUsername();
            String password = createUpdateEmployeeView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(createUpdateEmployeeView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(createUpdateEmployeeView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(createUpdateEmployeeView.getContentPane(), "Registration successful!");
                }
            }
        }
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String username = createUpdateEmployeeView.getUsername();
            String password = createUpdateEmployeeView.getPassword();
            String id = createUpdateEmployeeView.getId();

            Notification<Boolean> registerNotification = userService.update(Long.parseLong(id), username, password);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(createUpdateEmployeeView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(createUpdateEmployeeView.getContentPane(), "Update not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(createUpdateEmployeeView.getContentPane(), "Update successful!");
                }
            }
        }
    }
}
