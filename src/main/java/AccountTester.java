import clients.Bot;
import clients.User;

import javax.swing.*;


public class AccountTester {
    public static void main(String[] args) {
        User client = new User();
        Bot bottyBoy = new Bot();

        System.out.println(client);
        System.out.println("Resetting key to defgh");
        client.setKey("defgh");
        System.out.println(client);
        JFrame mainStage = new JFrame();
        mainStage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainStage.setSize(400, 300);
        JButton endButton = new JButton("Click to quit");
        endButton.addActionListener(event -> System.exit(0));
        mainStage.add(endButton);

        mainStage.setVisible(true);
    }
}