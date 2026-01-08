package ui;

import javax.swing.*;
import java.awt.*;

public class StudentDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private JTextField nameField;
    private JTextField rollNumberField;
    private JTextField gradeField;
    private boolean succeeded;
    private boolean isEditMode;

    public StudentDialog(Frame parent, String title, String name, int rollNumber, String grade) {
        super(parent, title, true);
        this.isEditMode = (title != null && title.toLowerCase().contains("edit"));

        getContentPane().setBackground(new Color(245, 247, 250)); // Match MainFrame

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(63, 81, 181)); // Indigo header for dialog
        JLabel headerLabel = new JLabel(title);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.insets = new Insets(10, 10, 10, 10);

        JLabel lbName = new JLabel("Name: ");
        lbName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbName, cs);

        nameField = new JTextField(20);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setText(name);
        nameField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(nameField, cs);

        JLabel lbRollNumber = new JLabel("Roll Number: ");
        lbRollNumber.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbRollNumber, cs);

        rollNumberField = new JTextField(20);
        rollNumberField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rollNumberField.setText(rollNumber == -1 ? "" : String.valueOf(rollNumber));
        rollNumberField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        if (isEditMode) {
            rollNumberField.setEditable(false);
            rollNumberField.setBackground(new Color(230, 230, 230));
        }
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(rollNumberField, cs);

        JLabel lbGrade = new JLabel("Grade: ");
        lbGrade.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbGrade, cs);

        gradeField = new JTextField(20);
        gradeField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gradeField.setText(grade);
        gradeField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(gradeField, cs);

        JButton btnSave = new JButton(isEditMode ? "Update" : "Add");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBackground(new Color(46, 204, 113)); // Emerald
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);

        btnSave.addActionListener(e -> {
            if (validateInput()) {
                succeeded = true;
                dispose();
            }
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setBackground(new Color(239, 83, 80)); // Soft Red
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        btnCancel.setBorderPainted(false);

        btnCancel.addActionListener(e -> {
            succeeded = false;
            dispose();
        });

        JPanel bp = new JPanel();
        bp.setOpaque(false);
        bp.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        bp.add(btnSave);
        bp.add(new JLabel("   "));
        bp.add(btnCancel);

        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(rollNumberField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Roll Number must be a valid integer.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (gradeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Grade cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public String getName() {
        return nameField.getText().trim();
    }

    public int getRollNumber() {
        return Integer.parseInt(rollNumberField.getText().trim());
    }

    public String getGrade() {
        return gradeField.getText().trim();
    }

    public boolean isSucceeded() {
        return succeeded;
    }
}
