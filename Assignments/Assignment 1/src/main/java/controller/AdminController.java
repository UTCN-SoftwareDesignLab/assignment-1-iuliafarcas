package controller;

import view.AdminView;
import view.CreateUpdateEmployeeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminController {

    private final AdminView adminView;
    private final CreateUpdateEmployeeView createUpdateEmployeeView;

    public AdminController(AdminView adminView, CreateUpdateEmployeeView createUpdateEmployeeView)
    {

        this.adminView = adminView;
        this.createUpdateEmployeeView = createUpdateEmployeeView;

        adminView.setCreateButtonListener(new AddButtonListener());
    }

    private class AddButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            createUpdateEmployeeView.setVisible(true);
        }
    }

}
