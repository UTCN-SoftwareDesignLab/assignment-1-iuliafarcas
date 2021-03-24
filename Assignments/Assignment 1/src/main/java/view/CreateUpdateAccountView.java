package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class CreateUpdateAccountView extends JFrame {

    private JTextField tfId;
    private JTextField tfType;
    private JTextField tfBalance;
    private JTextField tfCNP;
    private JTextField tfDate;

    private JButton addBtn;
    private JButton updateBtn;


    public CreateUpdateAccountView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(tfId);
        add(tfType);
        add(tfBalance);
        add(tfCNP);
        add(tfDate);
        add(addBtn);
        add(updateBtn);

        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private void initializeFields() {
        tfId = new JTextField("ID:Used only for update");
        tfType = new JTextField("Type");
        tfBalance = new JTextField("Balance");
        tfCNP = new JTextField("CNP");
        tfDate = new JTextField("Date");
        addBtn = new JButton("Register");
        updateBtn = new JButton("Update");
    }

    public void setCreateButtonListener(ActionListener createButtonListener) {
        addBtn.addActionListener(createButtonListener);
    }

    public void setUpdateButtonListener(ActionListener updateButtonListener) {
        updateBtn.addActionListener(updateButtonListener);
    }

    public String getID() { return tfId.getText(); }

    public String gettfType() { return tfType.getText(); }

    public String getBalance() { return tfBalance.getText(); }

    public String getcnp() { return tfCNP.getText(); }

    public String getDate() { return tfDate.getText(); }
}
