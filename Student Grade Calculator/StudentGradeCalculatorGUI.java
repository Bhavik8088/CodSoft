import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class StudentGradeCalculatorGUI extends JFrame {

    private JPanel subjectsPanel;
    private ArrayList<JTextField> markFields;
    private JLabel resultLabel;
    private JLabel gradeLabel;
    private JScrollPane scrollPane;

    // Colors
    private final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel Blue
    private final Color ACCENT_COLOR = new Color(100, 149, 237); // Cornflower Blue
    private final Color BG_COLOR = new Color(240, 248, 255); // Alice Blue

    public StudentGradeCalculatorGUI() {
        setTitle("Student Grade Calculator");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_COLOR);
        setLayout(new BorderLayout());

        markFields = new ArrayList<>();

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        JLabel titleLabel = new JLabel("Grade Calculator");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Center Panel (Subjects)
        subjectsPanel = new JPanel();
        subjectsPanel.setLayout(new BoxLayout(subjectsPanel, BoxLayout.Y_AXIS));
        subjectsPanel.setBackground(Color.WHITE);

        // Initial subjects
        addSubjectField();
        addSubjectField();
        addSubjectField();

        scrollPane = new JScrollPane(subjectsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel (Controls & Result)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(BG_COLOR);
        bottomPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(BG_COLOR);

        JButton addButton = createStyledButton("Add Subject", PRIMARY_COLOR);
        addButton.addActionListener(e -> {
            addSubjectField();
            validate();
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });

        JButton calculateButton = createStyledButton("Calculate", new Color(46, 139, 87)); // Sea Green
        calculateButton.addActionListener(e -> calculateResults());

        buttonPanel.add(addButton);
        buttonPanel.add(calculateButton);
        bottomPanel.add(buttonPanel);

        bottomPanel.add(Box.createVerticalStrut(20));

        // Results
        resultLabel = new JLabel("Average: 0.00%");
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(resultLabel);

        gradeLabel = new JLabel("Grade: -");
        gradeLabel.setFont(new Font("Segoe UI", Font.BOLD, 40)); // Big grade
        gradeLabel.setForeground(PRIMARY_COLOR);
        gradeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(gradeLabel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addSubjectField() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(5, 10, 5, 10));

        JLabel label = new JLabel("Subject " + (markFields.size() + 1) + ": ");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTextField field = new JTextField(10);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        markFields.add(field);
        panel.add(label);
        panel.add(field);

        subjectsPanel.add(panel);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void calculateResults() {
        int totalMarks = 0;
        int count = 0;

        for (JTextField field : markFields) {
            try {
                int mark = Integer.parseInt(field.getText().trim());
                if (mark < 0 || mark > 100) {
                    JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100.");
                    return;
                }
                totalMarks += mark;
                count++;
            } catch (NumberFormatException e) {
                // Ignore empty or invalid fields just for robustness, or we could strict error
                if (!field.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter valid integers for marks.");
                    return;
                }
            }
        }

        if (count == 0)
            return;

        double average = (double) totalMarks / count;
        String finalGrade = StudentGradeCalculator.calculateGrade(average);

        // Animation: Count up average
        animateResult(average, finalGrade);
    }

    private void animateResult(double targetAverage, String targetGrade) {
        // Reset
        resultLabel.setText("Calculating...");
        gradeLabel.setText("-");

        final double[] currentAvg = { 0.0 };

        Timer timer = new Timer(20, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentAvg[0] < targetAverage) {
                    currentAvg[0] += 1.5; // Increment speed
                    if (currentAvg[0] > targetAverage)
                        currentAvg[0] = targetAverage;

                    resultLabel.setText(String.format("Average: %.2f%%", currentAvg[0]));
                } else {
                    timer.stop();
                    // Final reveal
                    gradeLabel.setText("Grade: " + targetGrade);
                    // Minimal "pop" color effect
                    gradeLabel.setForeground(getGradeColor(targetGrade));
                }
            }
        });
        timer.start();
    }

    private Color getGradeColor(String grade) {
        switch (grade) {
            case "A":
                return new Color(34, 139, 34); // Forest Green
            case "B":
                return new Color(50, 205, 50); // Lime Green
            case "C":
                return new Color(255, 165, 0); // Orange
            case "D":
                return new Color(255, 69, 0); // Red-Orange
            default:
                return Color.RED;
        }
    }

    public static void main(String[] args) {
        // Set Look and Feel to System default for better integration
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            new StudentGradeCalculatorGUI().setVisible(true);
        });
    }
}
