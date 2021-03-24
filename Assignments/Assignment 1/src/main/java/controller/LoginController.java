package controller;

import model.Role;
import model.User;
import model.validation.Notification;
import service.user.AuthenticationService;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final AdminView adminView;
    private final EmployeeView employeeView;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, AdminView adminView, EmployeeView employeeView) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.adminView = adminView;
        this.employeeView = employeeView;

        loginView.setLoginButtonListener(new LoginButtonListener());
        //loginView.setRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");

                User user = loginNotification.getResult();

                if(getRole(user).equals(ADMINISTRATOR))
                {
                    adminView.setVisible(true);
                    loginView.setVisible(false);
                }
                else if(getRole(user).equals(EMPLOYEE))
                {
                    employeeView.setVisible(true);
                    loginView.setVisible(false);
                }

            }
        }
    }

    private String getRole(User user)
    {
        for(Role r:user.getRoles())
            if(r.getRole().equals(ADMINISTRATOR))
                return ADMINISTRATOR;
        return user.getRoles().get(0).getRole();
    }

    /*private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                }
            }
        }
    }*/


}
