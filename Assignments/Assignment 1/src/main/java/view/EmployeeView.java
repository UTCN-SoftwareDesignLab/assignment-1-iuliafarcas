package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame {

    private JButton addBtn;
    //private JButton updateBtn;
    private JButton viewBtn;
    private JButton operationsBtn;

    public EmployeeView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(addBtn);
        //add(updateBtn);
        add(viewBtn);
        add(operationsBtn);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        addBtn = new JButton("Add / Update Client");
        //updateBtn = new JButton("Update Client");
        viewBtn = new JButton("View Clients");
        operationsBtn = new JButton("Account Operations");
    }

    public void setCreateButtonListener(ActionListener createButtonListener) {
        addBtn.addActionListener(createButtonListener);
    }

    public void accOpButtonListener(ActionListener accOpButtonListener) {
        operationsBtn.addActionListener(accOpButtonListener);
    }

}
