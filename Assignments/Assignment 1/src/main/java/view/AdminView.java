package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminView extends JFrame {

    private JButton addBtn;
    //private JButton updateBtn;
    private JButton viewBtn;
    private JButton deleteBtn;
    private JButton reportBtn;

    public AdminView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(addBtn);
        //add(updateBtn);
        add(viewBtn);
        add(deleteBtn);
        add(reportBtn);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        addBtn = new JButton("Add / Update Employee");
        //updateBtn = new JButton("Update Employee");
        viewBtn = new JButton("View Employee");
        deleteBtn = new JButton("Delete Employee");
        reportBtn = new JButton("Report");
    }

    public void setCreateButtonListener(ActionListener createButtonListener) {
        addBtn.addActionListener(createButtonListener);
        //updateBtn.addActionListener(createButtonListener);
    }

}
