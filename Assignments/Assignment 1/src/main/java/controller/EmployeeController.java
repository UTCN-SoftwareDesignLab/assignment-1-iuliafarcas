package controller;

import view.CreateUpdateAccountView;
import view.CreateUpdateClientView;
import view.EmployeeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final CreateUpdateClientView createUpdateClientView;
    private final CreateUpdateAccountView createUpdateAccountView;

    public EmployeeController(EmployeeView employeeView, CreateUpdateClientView createUpdateClientView, CreateUpdateAccountView createUpdateAccountView) {
        this.employeeView = employeeView;
        this.createUpdateClientView = createUpdateClientView;
        this.createUpdateAccountView = createUpdateAccountView;

        employeeView.setCreateButtonListener(new AddButtonListener());
        employeeView.accOpButtonListener(new AccountButtonListener());
    }


    private class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            createUpdateClientView.setVisible(true);
        }
    }

    private class AccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            createUpdateAccountView.setVisible(true);
        }
    }
}
