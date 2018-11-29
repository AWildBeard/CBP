package cbp.ui;

import cbp.clients.Accounts;
import cbp.ui.components.AddClientTab;
import cbp.ui.components.CurrentAccountsTab;
import cbp.ui.components.DeleteClientTab;
import cbp.ui.components.FindClientTab;

import javax.swing.*;
import java.awt.*;

public class AccountTester extends JFrame {
    private JTabbedPane mainScene;
    private static Accounts accounts;

    public static void main(String[] args) {
        accounts = new Accounts();
        AccountTester mainStage = new AccountTester();

        mainStage.setVisible(true);
    }

    public AccountTester() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 600);
        mainScene = new JTabbedPane();

        mainScene.addTab("Add a Client", new AddClientTab(accounts));
        mainScene.addTab("Find a Client", new FindClientTab(accounts));
        mainScene.addTab("Delete a Client", new DeleteClientTab(accounts));
        mainScene.addTab("Current Accounts", new CurrentAccountsTab(accounts));

        this.add(mainScene);
    }

    private JComponent createFiller(String filler) {
        JLabel label = new JLabel(filler);
        label.setHorizontalAlignment(JLabel.CENTER);

        JPanel panel = new JPanel(false);
        panel.setLayout(new GridLayout(1, 1));

        panel.add(label);

        return panel;
    }
}