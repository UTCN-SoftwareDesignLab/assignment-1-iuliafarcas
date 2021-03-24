package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class CreateUpdateEmployeeView extends JFrame {

    private JTextField tfId;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton addBtn;
    private JButton updateBtn;

    public CreateUpdateEmployeeView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(tfId);
        add(tfUsername);
        add(tfPassword);
        add(addBtn);
        add(updateBtn);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private void initializeFields() {
        tfId = new JTextField("Used only for update");
        tfUsername = new JTextField();
        tfPassword = new JTextField();
        addBtn = new JButton("Register");
        updateBtn = new JButton("Update");
        //btnRegister = new JButton("Register");
    }

    public void setCreateButtonListener(ActionListener createButtonListener) {
        addBtn.addActionListener(createButtonListener);
    }

    public void setUpdateButtonListener(ActionListener updateButtonListener) {
        updateBtn.addActionListener(updateButtonListener);
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public String getId() { return  tfId.getText(); }
}
