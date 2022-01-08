package ca.cmpt213.a4.client.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Class that asks for a consumable item number and then returns it
 */
public class RemoveGUI extends JDialog implements ActionListener {

    int itemNumber;
    int listSize;

    Box mainBox;

    JTextField itemToRemove;

    JButton remove;
    JButton cancel;

    /**
     * Main constructor to create the dialog window
     * @param parent - the frame which the dialog window will appear on
     * @param listSize - the size of the consumable arraylist
     */
    public RemoveGUI(JFrame parent, int listSize) {

        super(parent, true);
        this.listSize = listSize;

        mainBox = Box.createVerticalBox();

        JPanel textPanel = new JPanel();
        JLabel name = new JLabel("Item to Remove: ");
        itemToRemove = new JTextField(10);
        itemToRemove.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent key) {
                if ( (key.getKeyChar() >= '0' && key.getKeyChar() <= '9' || key.getKeyChar()== 8)) {
                    itemToRemove.setEditable(true);
                } else {
                    itemToRemove.setEditable(false);
                }
            }
        });
        textPanel.add(name);
        textPanel.add(itemToRemove);
        mainBox.add(textPanel);

        JPanel buttonPanel = new JPanel();
        remove = new JButton("Remove Item");
        remove.addActionListener(this);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        buttonPanel.add(remove);
        buttonPanel.add(cancel);
        mainBox.add(buttonPanel);

        super.add(mainBox);
    }

    /**
     * Method that makes the remove dialog visible and also returns the consumable item number
     * used as reference:
     * https://stackoverflow.com/questions/4089311/how-can-i-return-a-value-from-a-jdialog-box-to-the-parent-jframe
     * @return - the item number to be removed
     */
    public int showRemoveDialog() {
        setTitle("Remove Item");
        pack();
        setLocationRelativeTo(getParent());
        setVisible(true);
        return itemNumber;
    }

    /**
     * Method for performing actions
     * @param e - the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Remove Item")) {

            String stringNumber = itemToRemove.getText();

            if (itemToRemove.getText().isEmpty()) {
                JOptionPane.showMessageDialog(mainBox, "<html> Invalid Input <br>" +
                        "Text Field Empty");
                return;
            }

            this.itemNumber = Integer.parseInt(stringNumber);

            if (itemNumber < 0 || itemNumber > listSize) {
                JOptionPane.showMessageDialog(mainBox, "<html> Invalid Input <br>" +
                        "This item number does not exist");
                return;
            }

            setVisible(false);

        } else if (e.getActionCommand().equals("Cancel")) {
            setVisible(false);
        }
    }


}
