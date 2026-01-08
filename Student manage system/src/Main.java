import core.StudentManagementSystem;
import ui.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Initialize the Logic System
            StudentManagementSystem sms = new StudentManagementSystem();

            // Pass it to the GUI
            MainFrame frame = new MainFrame(sms);
            frame.setVisible(true);
        });
    }
}
