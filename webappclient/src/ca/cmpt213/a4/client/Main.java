package ca.cmpt213.a4.client;

import ca.cmpt213.a4.client.view.MainGUI;

import javax.swing.*;

/**
 * Class that runs the program
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
