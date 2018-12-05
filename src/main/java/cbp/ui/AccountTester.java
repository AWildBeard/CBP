package cbp.ui;

import cbp.clients.Accounts;
import cbp.ui.components.AddClientTab;
import cbp.ui.components.CurrentAccountsTab;
import cbp.ui.components.DeleteClientTab;
import cbp.ui.components.FindClientTab;

import javax.swing.*;
import java.awt.*;

/**
 * @author Michael Mitchell
 */
public class AccountTester extends JFrame {

    /**
     * The main tabbed scene for the Window
     */
    private JTabbedPane mainScene;

    /**
     * The accounts object that stores all Clients
     */
    private static Accounts accounts;

    public static void main(String[] args) {
        accounts = new Accounts();
        AccountTester mainStage = new AccountTester();

        mainStage.setVisible(true); // Display the scene
    }

    /**
     * Constructor to create the scene
     */
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
}