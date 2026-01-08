package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import core.Student;
import core.StudentManagementSystem;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private StudentManagementSystem sms;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public MainFrame(StudentManagementSystem sms) {
        this.sms = sms;

        setTitle("Student Management System");
        setSize(950, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 247, 250)); // Light Grayish Blue background

        // --- Gradient Header ---
        JPanel headerPanel = new GradientPanel(new Color(63, 81, 181), new Color(103, 58, 183)); // Indigo to Deep
                                                                                                 // Purple
        headerPanel.setPreferredSize(new Dimension(getWidth(), 90));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Student Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // --- Control Panel ---
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        controlPanel.setOpaque(false);

        // Vibrant Buttons
        JButton addButton = createStyledButton("Add Student", new Color(46, 204, 113)); // Emerald
        JButton editButton = createStyledButton("Edit Student", new Color(33, 150, 243)); // Blue
        JButton deleteButton = createStyledButton("Delete Student", new Color(239, 83, 80)); // Soft Red

        searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));

        JButton searchButton = createStyledButton("Search", new Color(63, 81, 181)); // Indigo
        JButton refreshButton = createStyledButton("Refresh", new Color(255, 152, 0)); // Orange
        JButton excelButton = createStyledButton("Open in Excel", new Color(33, 115, 70)); // Excel Green

        controlPanel.add(addButton);
        controlPanel.add(editButton);
        controlPanel.add(deleteButton);
        controlPanel.add(new JLabel("     "));
        controlPanel.add(searchField);
        controlPanel.add(searchButton);
        controlPanel.add(refreshButton);
        controlPanel.add(excelButton);

        // --- Table ---
        String[] columnNames = { "Name", "Roll Number", "Grade" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawEmptyGrid(g);
            }

            private void drawEmptyGrid(Graphics g) {
                if (getRowCount() < 1) { // If empty or fewer rows than viewport
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(getGridColor());
                    int rowHeight = getRowHeight();
                    int startY = getRowCount() * rowHeight;
                    int height = getHeight();

                    // Draw horizontal lines
                    for (int y = startY; y < height; y += rowHeight) {
                        g2.drawLine(0, y, getWidth(), y);
                    }

                    // Draw vertical lines
                    int totalWidth = 0;
                    for (int i = 0; i < getColumnCount(); i++) {
                        totalWidth += getColumnModel().getColumn(i).getWidth();
                        g2.drawLine(totalWidth - 1, 0, totalWidth - 1, height);
                    }
                }
            }
        };
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        studentTable.setRowHeight(30);
        studentTable.setGridColor(new Color(150, 150, 150)); // Even darker grid
        studentTable.setShowVerticalLines(true);
        studentTable.setShowHorizontalLines(true);
        studentTable.setIntercellSpacing(new Dimension(1, 1));
        studentTable.setFillsViewportHeight(true); // Paint background in empty space

        // Header styling
        // Header styling
        JTableHeader header = studentTable.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                l.setFont(new Font("Segoe UI", Font.BOLD, 15));
                l.setBackground(new Color(63, 81, 181)); // Indigo header
                l.setForeground(Color.WHITE);
                l.setHorizontalAlignment(JLabel.CENTER);
                l.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(220, 220, 220)),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                return l;
            }
        });

        studentTable.setSelectionBackground(new Color(197, 202, 233)); // Light Indigo selection
        studentTable.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Layout
        add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(controlPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Action Listeners
        addButton.addActionListener(e -> addStudent());
        editButton.addActionListener(e -> editStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        searchButton.addActionListener(e -> searchStudent());
        refreshButton.addActionListener(e -> refreshTable());
        excelButton.addActionListener(e -> openInExcel());

        refreshTable();
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Helper class for Gradient Background
    class GradientPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private Color color1, color2;

        public GradientPanel(Color color1, Color color2) {
            this.color1 = color1;
            this.color2 = color2;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, color1, w, 0, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Student> students = sms.getAllStudents();
        for (Student s : students) {
            tableModel.addRow(new Object[] { s.getName(), s.getRollNumber(), s.getGrade() });
        }
    }

    private void addStudent() {
        StudentDialog dialog = new StudentDialog(this, "Add Student", "", -1, "");
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            Student newStudent = new Student(dialog.getName(), dialog.getRollNumber(), dialog.getGrade());
            // Check for duplicate roll number if needed
            if (sms.searchStudent(newStudent.getRollNumber()) != null) {
                JOptionPane.showMessageDialog(this, "Roll Number already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            sms.addStudent(newStudent);
            refreshTable();
        }
    }

    private void editStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to edit.");
            return;
        }

        int rollNumber = (int) tableModel.getValueAt(selectedRow, 1);
        Student student = sms.searchStudent(rollNumber);

        if (student != null) {
            StudentDialog dialog = new StudentDialog(this, "Edit Student", student.getName(), student.getRollNumber(),
                    student.getGrade());
            dialog.setVisible(true);

            if (dialog.isSucceeded()) {
                student.setName(dialog.getName());
                student.setGrade(dialog.getGrade());
                // RolNumber usually shouldn't change, or if it does, it's complex. Dialog has
                // it disabled for edit.
                sms.updateStudent(student);
                refreshTable();
            }
        }
    }

    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
            return;
        }

        int rollNumber = (int) tableModel.getValueAt(selectedRow, 1);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete student with Roll No: " + rollNumber + "?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            sms.removeStudent(rollNumber);
            refreshTable();
        }
    }

    private void searchStudent() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            refreshTable();
            return;
        }

        try {
            int rollNumber = Integer.parseInt(searchText);
            Student student = sms.searchStudent(rollNumber);
            tableModel.setRowCount(0);
            if (student != null) {
                tableModel.addRow(new Object[] { student.getName(), student.getRollNumber(), student.getGrade() });
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Roll Number.");
        }
    }

    private void openInExcel() {
        try {
            java.io.File file = new java.io.File("students.csv");
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                JOptionPane.showMessageDialog(this, "Data file 'students.csv' not found!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not open Excel: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
