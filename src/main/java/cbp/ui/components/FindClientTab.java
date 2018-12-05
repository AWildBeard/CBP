package cbp.ui.components;

import cbp.clients.Accounts;
import cbp.clients.Bot;
import cbp.clients.Client;
import cbp.clients.User;

import javax.swing.*;
import java.awt.*;

/**
 * @author Michael Mitchell
 */
public class FindClientTab extends JPanel {

    /**
     * Single constructor to create the Find Client Tab
     * @param accounts The accounts object to retrieve clients from
     * @see cbp.clients.Accounts
     */
    public FindClientTab(Accounts accounts) {
        super(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        JLabel label = new JLabel("Enter a \"User name\" or \"Bot File name\" to search for");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);
        this.add(label, constraints);

        JTextField searchField = new JTextField(40);
        constraints.gridy = 1;
        constraints.ipady = 5;
        this.add(searchField, constraints);

        JButton submitButton = new JButton("Search");
        constraints.ipady = 0;
        constraints.gridx = 1;
        constraints.gridy = 1;
        this.add(submitButton, constraints);

        submitButton.addActionListener(action -> {
            String searchQuery = searchField.getText();

            if (! searchQuery.equals("")) {
                Client client = accounts.getClient(searchQuery);
                if (client instanceof User) {
                    StringBuilder message = new StringBuilder();
                    message.append(String.format("Found user \"%s\"\n\nDetails:\n", searchQuery));

                    message.append(String.format(User.toStringFormat.replace("\t", ""),
                            (Object[]) User.toStringHeaders));

                    message.append(String.format(Client.toStringFormat.replace("\t",
                            "") + "\n",
                            (Object[]) Client.toStringHeaders));

                    message.append(client.toString().replace("\t", ""));

                    // JOptionPane reformats my fixed-width text from above
                    JOptionPane.showMessageDialog(this, message,
                            "User details", JOptionPane.INFORMATION_MESSAGE);
                } else if (client instanceof Bot) {
                    StringBuilder message = new StringBuilder();
                    message.append(String.format("Found bot \"%s\"\n\nDetails:\n", searchQuery));

                    message.append(String.format(Bot.toStringFormat.replace("\t",
                            ""), (Object[]) Bot.toStringHeaders));

                    message.append(String.format(Client.toStringFormat.replace("\t",
                            "") + "\n", (Object[]) Client.toStringHeaders));

                    message.append(client.toString().replace("\t", ""));

                    // JOptionPane reformats my fixed-width text :(
                    JOptionPane.showMessageDialog(this, message,
                            "Bot details", JOptionPane.INFORMATION_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(this, "Failed to find Client!",
                            "Client not found!", JOptionPane.ERROR_MESSAGE);
            }

            // Clear the text field after searches.
            searchField.setText("");
        });
    }
}
