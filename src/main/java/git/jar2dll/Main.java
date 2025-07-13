package git.jar2dll;

import git.jar2dll.gui.ImageInjectorGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        SwingUtilities.invokeLater(() -> {
            new ImageInjectorGUI().setVisible(true);
        });
    }
}
