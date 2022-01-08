package ca.cmpt213.a4.client.view;

import ca.cmpt213.a4.client.model.ConsumableFactory;

import ca.cmpt213.a4.client.model.Consumable;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Class that asks for user input and then creates a consumable item out of them
 */
public class AddGUI extends JDialog implements ActionListener {

    Consumable consumable;
    ConsumableFactory factory = new ConsumableFactory();

    Box mainBox;

    JComboBox typeComboBox;
    JTextField nameTextField;
    JTextField notesTextField;
    JTextField priceTextField;
    JTextField weightTextField;
    DatePicker expiryDateField;

    JLabel size;

    String[] types = { "Food","Drink" };

    JButton add;
    JButton cancel;

    /**
     * Main Constructor to create the dialog window
     * @param parent - the frame which the dialog window will appear on
     */
    public AddGUI(JFrame parent) {

        super(parent, true);

        mainBox = Box.createVerticalBox();

        JPanel typePanel = new JPanel();
        JLabel type = new JLabel("Type: ");
        typeComboBox = new JComboBox(types);
        typeComboBox.addActionListener(this);
        typePanel.add(type);
        typePanel.add(typeComboBox);
        mainBox.add(typePanel);

        JPanel namePanel = new JPanel();
        JLabel name = new JLabel("Name: ");
        nameTextField = new JTextField(20);
        namePanel.add(name);
        namePanel.add(nameTextField);
        mainBox.add(namePanel);

        JPanel notesPanel = new JPanel();
        JLabel notes = new JLabel("Notes: ");
        notesTextField = new JTextField(20);
        notesPanel.add(notes);
        notesPanel.add(notesTextField);
        mainBox.add(notesPanel);

        JPanel pricePanel = new JPanel();
        JLabel price = new JLabel("Price: ");
        priceTextField = new JTextField(20);
        priceTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent key) {
                if ( (key.getKeyChar() >= '0' && key.getKeyChar() <= '9') || key.getKeyChar() == '.'
                        || key.getKeyChar()== 8) {
                    priceTextField.setEditable(true);
                } else {
                    priceTextField.setEditable(false);
                }
            }
        });
        pricePanel.add(price);
        pricePanel.add(priceTextField);
        mainBox.add(pricePanel);

        JPanel weightPanel = new JPanel();
        size = new JLabel("Weight: ");
        weightTextField = new JTextField(20);
        weightTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent key) {
                if ( (key.getKeyChar() >= '0' && key.getKeyChar() <= '9') || key.getKeyChar() == '.'
                        || key.getKeyChar()== 8) {
                    weightTextField.setEditable(true);
                } else {
                    weightTextField.setEditable(false);
                }
            }
        });
        weightPanel.add(size);
        weightPanel.add(weightTextField);
        mainBox.add(weightPanel);

        JPanel datePanel = new JPanel();
        JLabel date = new JLabel("Expiry Date: ");
        expiryDateField = new DatePicker();
        datePanel.add(date);
        datePanel.add(expiryDateField);
        mainBox.add(datePanel);

        JPanel buttonPanel = new JPanel();
        add = new JButton("Add Item");
        add.addActionListener(this);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        buttonPanel.add(add);
        buttonPanel.add(cancel);
        mainBox.add(buttonPanel);

        super.add(mainBox);

    }

    /**
     * Method that makes the add dialog visible and also returns the consumable item
     * used as reference:
     * https://stackoverflow.com/questions/4089311/how-can-i-return-a-value-from-a-jdialog-box-to-the-parent-jframe
     * @return - a consumable item made from the user inputs
     */
    public Consumable showAddDialog() {
        setTitle("Add Item");
        pack();
        setLocationRelativeTo(getParent());
        setVisible(true);
        return consumable;
    }

    /**
     * Method for performing actions
     * @param e - the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        String stringType = (String)typeComboBox.getSelectedItem();

        if (stringType == "Drink") {
            size.setText("Volume: ");
        } else if (stringType == "Food") {
            size.setText("Weight: ");
        }

        if (e.getActionCommand().equals("Add Item")) {

            String name = nameTextField.getText();
            String notes = notesTextField.getText();
            String itemPrice = priceTextField.getText();
            String itemWeight = weightTextField.getText();
            LocalDate date = expiryDateField.getDate();

            if (nameTextField.getText().isEmpty()|| date == null ||
                    priceTextField.getText().isEmpty()|| weightTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(mainBox, "<htmL> Invalid Input <br> " +
                        "Only Notes Can Be Empty");
                return;
            }

            int type;
            if (stringType == "Food") {
                type = 1;
            } else {
                type = 2;
            }
            double price = Double.parseDouble(itemPrice);
            double size = Double.parseDouble(itemWeight);
            LocalDateTime expiryDate =  date.atStartOfDay();

            consumable = factory.getInstance(type, name, notes, price, size, expiryDate);


            setVisible(false);

        } else if (e.getActionCommand().equals("Cancel")) {
            setVisible(false);
        }
    }

}
