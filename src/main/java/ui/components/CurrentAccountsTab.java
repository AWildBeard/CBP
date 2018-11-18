package ui.components;

import clients.Accounts;

import javax.swing.*;
import java.awt.*;

public class CurrentAccountsTab extends JPanel {
    public CurrentAccountsTab(Accounts accounts) {
        super(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JButton refreshButton = new JButton("Refresh");
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        this.add(refreshButton, constraints);

        JButton readFileButton = new JButton("Open File");
        constraints.gridx = 1;
        this.add(readFileButton, constraints);

        JTextArea display = new JTextArea();
        display.setFont(Font.getFont(Font.MONOSPACED));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 5;
        this.add(display, constraints);

        refreshButton.addActionListener(action -> {
            display.setText(accounts.toString().replace("\t", " "));
        });

    }
}
