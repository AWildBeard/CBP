package ui.components;

import clients.Accounts;
import clients.Bot;
import clients.User;
import clients.exceptions.InvalidBotCategoryException;
import clients.exceptions.InvalidPasswordException;

import javax.swing.*;
import java.awt.*;

public class AddClientTab extends JPanel {
    public AddClientTab(Accounts accounts) {
        super(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JComboBox<String> clientTypeSelector = new JComboBox<>();

        clientTypeSelector.setEditable(false);
        clientTypeSelector.addItem("User");
        clientTypeSelector.addItem("Bot");
        clientTypeSelector.setSelectedItem("User");

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 30, 10, 30);
        this.add(clientTypeSelector, constraints);

        JButton submitButton = new JButton("Submit");

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = 1;
        constraints.gridy = 0;
        this.add(submitButton, constraints);

        JPanel clientTabCards = new JPanel(new CardLayout());

        JPanel userCard = new JPanel();
        userCard.setLayout(new GridLayout(2, 5, 10, 20));

        JPanel botCard = new JPanel();
        botCard.setLayout(new GridLayout(2, 6, 10, 20));

        // User Card
        userCard.add(createLabel("User Name"));
        userCard.add(createLabel("Full Name"));
        userCard.add(createLabel("Password"));
        userCard.add(createLabel("Key"));
        userCard.add(createLabel("Department Code"));

        JTextField userUserName = new JTextField(),
                userFullName = new JTextField(),
                userPassword = new JPasswordField(),
                userKey = new JPasswordField(),
                userDepartmentCode = new JTextField();

        userCard.add(userUserName);
        userCard.add(userFullName);
        userCard.add(userPassword);
        userCard.add(userKey);
        userCard.add(userDepartmentCode);

        // Bot Card
        JComboBox<String> categoryChooser = new JComboBox<>();
        Bot.validCategories.forEach(string -> categoryChooser.addItem((String) string));

        botCard.add(createLabel("Bot File Name"));
        botCard.add(createLabel("Bot Category"));
        botCard.add(createLabel("Created By"));
        botCard.add(createLabel("Password"));
        botCard.add(createLabel("Key"));
        botCard.add(createLabel("Date Created"));

        JTextField botFileName = new JTextField(),
                botCreatedBy = new JTextField(),
                botPassword = new JPasswordField(),
                botKey = new JPasswordField(),
                botDateCreated = new JTextField();

        botCard.add(botFileName);
        botCard.add(categoryChooser);
        botCard.add(botCreatedBy);
        botCard.add(botPassword);
        botCard.add(botKey);
        botCard.add(botDateCreated);

        clientTypeSelector.addItemListener(itemChange -> {
            CardLayout cl = (CardLayout) clientTabCards.getLayout();
            String item = (String) itemChange.getItem();

            cl.show(clientTabCards, item);
        });

        submitButton.addActionListener(action -> {
            if (clientTypeSelector.getSelectedItem().equals("User")) {
                try {
                    String userPasswordStr = new String(((JPasswordField) userPassword).getPassword()),
                            userKeyStr = new String(((JPasswordField) userKey).getPassword()),
                            userUserNameStr = userUserName.getText(),
                            userFullNameStr = userFullName.getText(),
                            userDepartmentCodeStr = userDepartmentCode.getText();

                    boolean hasUserKey = ! userKeyStr.equals(""),
                            hasUserName = ! userUserNameStr.equals(""),
                            hasUserFullName = ! userFullNameStr.equals("");

                    if (hasUserKey && hasUserName && hasUserFullName) {
                        accounts.addClient(new User(userPasswordStr, userKeyStr, userUserNameStr,
                                userFullNameStr, Integer.parseInt(userDepartmentCodeStr)
                        ));

                    } else {
                        // Password and Department Code are taken care of in libraries
                        if (! hasUserKey) {
                            throw new IllegalArgumentException("You must enter a key!");
                        } else if (! hasUserName) {
                            throw new IllegalArgumentException("You must enter a username!");
                        } else if (! hasUserFullName) {
                            throw new IllegalArgumentException("You must enter your full name!");
                        }
                    }

                    // Catch individually so we can do custom error messages
                } catch (NumberFormatException numberException) {
                    JOptionPane.showMessageDialog(this, "That is an invalid Department Code!",
                            "Invalid Bot Category", JOptionPane.ERROR_MESSAGE);

                } catch (InvalidPasswordException invalidPassword) {
                    JOptionPane.showMessageDialog(this, "Invalid password! " +
                                    "Make sure that the password is at least 8 characters,\n" +
                                    "contains a letter, contains a number, and contains any of " +
                                    "the following characters: # $ % &",
                            "Invalid Bot Category", JOptionPane.ERROR_MESSAGE);

                } catch (IllegalArgumentException illegalArgument) {
                    JOptionPane.showMessageDialog(this, illegalArgument.getMessage(),
                            illegalArgument.getMessage(), JOptionPane.ERROR_MESSAGE);

                } finally {
                    // Reset the text fields
                    userPassword.setText("");
                    userKey.setText("");
                    userUserName.setText("");
                    userFullName.setText("");
                    userDepartmentCode.setText("");
                }
            } else { // Issa bot
                try {
                    String botPasswordStr = new String(((JPasswordField) botPassword).getPassword()),
                            botKeyStr = new String(((JPasswordField) botKey).getPassword()),
                            botFileNameStr = botFileName.getText(),
                            botCategoryStr = (String) categoryChooser.getSelectedItem(),
                            botCreatedByStr = botCreatedBy.getText(),
                            botDateCreatedStr = botDateCreated.getText();

                    boolean hasBotKey = ! botKeyStr.equals(""),
                            hasBotFileName = ! botFileNameStr.equals(""),
                            hasBotCreatedBy = ! botCreatedByStr.equals("");

                    if (hasBotKey && hasBotFileName && hasBotCreatedBy) {
                        accounts.addClient(new Bot(botPasswordStr, botKeyStr, botFileNameStr,
                                botCategoryStr, botCreatedByStr, botDateCreatedStr
                        ));

                    } else {
                        if (! hasBotKey) {
                            throw new IllegalArgumentException("You must enter a key!");
                        } else if (! hasBotFileName) {
                            throw new IllegalArgumentException("You must enter a bot file name!");
                        } else if (! hasBotCreatedBy) {
                            throw new IllegalArgumentException("You must enter the Bot Created By field");
                        }
                    }

                    // Catch individually so we can do custom error messages.
                } catch (NumberFormatException numberException) {
                    JOptionPane.showMessageDialog(this, "That is an invalid date!" +
                                    "\nThe date must be in the format of DD/MM/YYYY",
                            "Invalid Bot Category", JOptionPane.ERROR_MESSAGE);

                } catch (InvalidBotCategoryException invalidCategory) {
                    JOptionPane.showMessageDialog(this, "That is an invalid bot category!",
                            "Invalid Bot Category", JOptionPane.ERROR_MESSAGE);

                } catch (InvalidPasswordException invalidPassword) {
                    JOptionPane.showMessageDialog(this, "Invalid password! " +
                                    "Make sure that the password is at least 8 characters,\n" +
                                    "contains a letter, contains a number, and contains any of " +
                                    "the following characters: # $ % &",
                            "Invalid Bot Category", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException illegalArgument) {
                    JOptionPane.showMessageDialog(this, illegalArgument.getMessage(),
                            illegalArgument.getMessage(), JOptionPane.ERROR_MESSAGE);

                } finally {
                    // Reset the text fields
                    botPassword.setText("");
                    botKey.setText("");
                    botFileName.setText("");
                    botCreatedBy.setText("");
                    botDateCreated.setText("");
                }
            }
        });

        clientTabCards.add(userCard, "User");
        clientTabCards.add(botCard, "Bot");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 5;
        constraints.weightx = 0.1;
        constraints.anchor = GridBagConstraints.PAGE_END;
        this.add(clientTabCards, constraints);
    }

    private JLabel createLabel(String content) {
        return new JLabel(content);
    }
}
