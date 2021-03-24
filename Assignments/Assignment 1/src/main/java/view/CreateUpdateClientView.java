package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class CreateUpdateClientView extends  JFrame{

    private JTextField tfId;
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextField tfAddress;
    private JTextField tfCNP;
    private JTextField tfCardNb;
    private JButton addBtn;
    private JButton updateBtn;

    public CreateUpdateClientView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(tfId);
        add(tfFirstName);
        add(tfLastName);
        add(tfAddress);
        add(tfCNP);
        add(tfCardNb);
        add(addBtn);
        add(updateBtn);

        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private void initializeFields() {
        tfId = new JTextField("Used only for update");
        tfFirstName = new JTextField("First Name");
        tfLastName = new JTextField("Last Name");
        tfAddress = new JTextField("Address");
        tfCNP = new JTextField("CNP");
        tfCardNb = new JTextField("Card number");
        addBtn = new JButton("Register");
        updateBtn = new JButton("Update");
    }

    public void setCreateButtonListener(ActionListener createButtonListener) {
        addBtn.addActionListener(createButtonListener);
    }

    public void setUpdateButtonListener(ActionListener updateButtonListener) {
        updateBtn.addActionListener(updateButtonListener);
    }

    public String getTfAddress() {
        return tfAddress.getText();
    }

    public String getTfCardNb() {
        return tfCardNb.getText();
    }

    public String getTfFirstName() {
        return tfFirstName.getText();
    }

    public String getTfCNP() {
        return tfCNP.getText();
    }

    public String getTfId() {
        return tfId.getText();
    }

    public String getTfLastName() {
        return tfLastName.getText();
    }
}
