package cbp.ui.components;

import cbp.clients.Accounts;
import cbp.util.CBPFile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;
import java.io.UncheckedIOException;

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

        readFileButton.addActionListener(action -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter extFilter = new FileNameExtensionFilter("CBP Data files",
                    "cbp");
            fileChooser.setFileFilter(extFilter);

            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    CBPFile newFile = new CBPFile(fileChooser.getSelectedFile().getPath());
                    newFile.clients().forEach(accounts::addClient);
                } catch (IOException error) {
                    JOptionPane.showMessageDialog(this, String.format("Failed to open file \"%s\"",
                            fileChooser.getSelectedFile().getName()), "Failed to open file",
                            JOptionPane.ERROR_MESSAGE);
                } catch (RuntimeException runtimeException) {
                    JOptionPane.showMessageDialog(this, runtimeException.getMessage(),
                            runtimeException.getMessage(), JOptionPane.ERROR_MESSAGE);

                } finally {
                        refreshButton.doClick();
                }
            }
        });

    }
}