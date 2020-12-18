package kg2020.task4;

import javax.swing.*;

public class MainWindow extends JDialog {
    private JPanel contentPane;

    public MainWindow() {
        setContentPane(contentPane);
        setModal(true);
    }

    public static void main(String[] args) {
        MainWindow dialog = new MainWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
