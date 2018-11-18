package ui.components;

import clients.Accounts;

import javax.swing.*;
import java.awt.*;

public class DeleteClientTab extends JPanel {
    public DeleteClientTab(Accounts accounts) {
        super(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JTextField textField = new JTextField(40);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.ipady = 5;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);
        this.add(textField, constraints);

        JLabel label = new JLabel("Enter a \"User name\" or \"Bot File name\" to delete");
        constraints.ipady = 0;
        constraints.gridy = 0;
        this.add(label, constraints);

        JButton deleteButton = new JButton("Delete");
        constraints.gridx = 1;
        constraints.gridy = 1;
        this.add(deleteButton, constraints);

        deleteButton.addActionListener(action -> {
            String userToDelete = textField.getText();

            if (accounts.deleteClient(userToDelete))
                JOptionPane.showMessageDialog(this,
                        String.format("Successfully deleted client: \"%s\"", userToDelete),
                        "Deleted a client", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this,
                        String.format("Could not delete client: \"%s\"", userToDelete),
                        "Failed to delete client", JOptionPane.ERROR_MESSAGE);

            textField.setText("");
        });
    }
}
