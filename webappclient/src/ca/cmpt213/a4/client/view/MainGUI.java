package ca.cmpt213.a4.client.view;

import ca.cmpt213.a4.client.control.ConsumableManager;
import ca.cmpt213.a4.client.model.Consumable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Class that creates and displays the main application gui
 */
public class MainGUI implements ActionListener {

    ConsumableManager manager = new ConsumableManager();

    JPanel topPanel;
    JLabel mainLabel;
    JScrollPane allItems;
    JScrollPane expiredItems;
    JScrollPane nonExpiredItems;
    JScrollPane expiringItems;
    JButton addItem;
    JButton removeItem;

    JFrame applicationFrame;
    JTabbedPane tabs;

    /**
     * Main constructor for creating the gui
     */
    public MainGUI() {

        try {
            manager.loadItems();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        applicationFrame = new JFrame("Consumable Item Tracker");
        applicationFrame.setSize(800,750);
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabs = new JTabbedPane();
        tabs.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));

        allItems = new JScrollPane(manager.allItems());
        tabs.addTab("All", allItems);

        expiredItems = new JScrollPane(manager.expiredItems());
        tabs.addTab("Expired", expiredItems);

        nonExpiredItems = new JScrollPane(manager.nonExpiredItems());
        tabs.addTab("Non Expired", nonExpiredItems);

        expiringItems = new JScrollPane(manager.inSevenDays());
        tabs.addTab("Expiring in 7 days", expiringItems);

        removeItem = new JButton("Remove Item");
        removeItem.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
        removeItem.addActionListener(this);
        addItem = new JButton("Add New Item");
        addItem.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
        addItem.addActionListener(this);

        topPanel = new JPanel();
        mainLabel = new JLabel("----------------------" +
                " My Consumable Tracker " +
                "----------------------");
        mainLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        topPanel.add(mainLabel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addItem);
        bottomPanel.add(removeItem);

        applicationFrame.add(topPanel, BorderLayout.NORTH);
        applicationFrame.add(tabs);
        applicationFrame.add(bottomPanel, BorderLayout.SOUTH);

        applicationFrame.setVisible(true);
        /**
         * used as reference:
         * https://stackoverflow.com/questions/6084039/create-custom-operation-for-setdefaultcloseoperation
         */
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure you wish to close the application?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    try {
                        manager.saveItems();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    System.exit(0);
                } else {
                    applicationFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        };
        applicationFrame.addWindowListener(exitListener);
    }

    /**
     * Method that updates the gui
     * It will be called after a removal or addition to the consumable arraylist
     */
    public void Update() {

        tabs.remove(allItems);
        allItems = new JScrollPane(manager.allItems());
        tabs.add("All", allItems);

        tabs.remove(expiredItems);
        expiredItems = new JScrollPane(manager.expiredItems());
        tabs.add("Expired", expiredItems);

        tabs.remove(nonExpiredItems);
        nonExpiredItems = new JScrollPane(manager.nonExpiredItems());
        tabs.add("Non Expired", nonExpiredItems);

        tabs.remove(expiringItems);
        expiringItems = new JScrollPane(manager.inSevenDays());
        tabs.add("Expiring in 7 days", expiringItems);

    }

    /**
     * Method for performing actions
     * @param e - the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add New Item")) {
            try {
                manager.addItem(applicationFrame);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Update();
        } else if (e.getActionCommand().equals("Remove Item")) {;
            try {
                manager.removeItem(applicationFrame);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Update();
        }
    }

}

