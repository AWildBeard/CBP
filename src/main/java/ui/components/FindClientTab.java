package ui.components;

import clients.Accounts;

import javax.swing.*;
import java.awt.*;

public class FindClientTab  extends JPanel {
    public FindClientTab(Accounts accounts) {
        GridBagConstraints constraints = new GridBagConstraints();

        JTextField searchField = new JTextField();
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(searchField, constraints);

        JButton submitButton = new JButton("Search");
        constraints.gridx = 1;
        constraints.gridy = 0;
        this.add(submitButton, constraints);
    }
}
