
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

public class CurrencyConverterApp {

    private ExchangeRateService service;
    private JFrame frame;
    private JComboBox<String> fromCurrencyBox;
    private JComboBox<String> toCurrencyBox;
    private JTextField amountField;
    private JLabel resultLabel;
    private JButton convertButton;

    // Modern Color Palette
    private static final Color PRIMARY_COLOR = new Color(75, 119, 190); // Nice Blue
    private static final Color HOVER_COLOR = new Color(50, 90, 160);
    private static final Color BG_COLOR = new Color(240, 242, 245);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(50, 50, 50);

    private static final String[] CURRENCIES = { "USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "INR", "BRL",
            "ZAR" };

    public CurrencyConverterApp() {
        service = new ExchangeRateService();
        initialize();
    }

    private void initialize() {
        // Setup Frame
        frame = new JFrame("Currency Master");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BG_COLOR);
        frame.setLayout(new GridBagLayout()); // For centering the card

        // Main Card Panel
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new GridBagLayout());
        cardPanel.setBackground(CARD_BG);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(30, 40, 40, 40)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0); // Vertical spacing

        // Title / Header
        JLabel titleLabel = new JLabel("Currency Converter", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        cardPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Real-time exchange rates", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0); // Extra space below header
        cardPanel.add(subtitleLabel, gbc);

        // Reset insets
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridwidth = 1;

        // Amount Field
        gbc.gridy++;
        cardPanel.add(createLabel("Amount"), gbc);

        amountField = new JTextField("1.00");
        styleTextField(amountField);
        gbc.gridy++;
        cardPanel.add(amountField, gbc);

        // Currencies Row (Split into two columns)
        gbc.gridy++;
        JPanel currencyPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        currencyPanel.setBackground(CARD_BG);

        // From
        JPanel fromPanel = new JPanel(new BorderLayout(0, 5));
        fromPanel.setBackground(CARD_BG);
        fromPanel.add(createLabel("From"), BorderLayout.NORTH);
        fromCurrencyBox = new JComboBox<>(CURRENCIES);
        styleComboBox(fromCurrencyBox);
        fromCurrencyBox.setSelectedItem("USD");
        fromPanel.add(fromCurrencyBox, BorderLayout.CENTER);
        currencyPanel.add(fromPanel);

        // To
        JPanel toPanel = new JPanel(new BorderLayout(0, 5));
        toPanel.setBackground(CARD_BG);
        toPanel.add(createLabel("To"), BorderLayout.NORTH);
        toCurrencyBox = new JComboBox<>(CURRENCIES);
        styleComboBox(toCurrencyBox);
        toCurrencyBox.setSelectedItem("EUR");
        toPanel.add(toCurrencyBox, BorderLayout.CENTER);
        currencyPanel.add(toPanel);

        cardPanel.add(currencyPanel, gbc);

        // Convert Button
        gbc.gridy++;
        gbc.insets = new Insets(25, 0, 15, 0); // More space before button
        convertButton = new JButton("CONVERT CURRENCY");
        styleButton(convertButton);
        cardPanel.add(convertButton, gbc);

        // Result Label
        gbc.gridy++;
        resultLabel = new JLabel("Welcome!", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        resultLabel.setForeground(TEXT_COLOR);
        resultLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
        cardPanel.add(resultLabel, gbc);

        // Add Card to Frame
        frame.add(cardPanel);

        // Action Listener
        convertButton.addActionListener(e -> convertCurrency());

        frame.setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(100, 100, 100));
        return label;
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
    }

    private void styleComboBox(JComboBox<String> box) {
        box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        box.setBackground(Color.WHITE);
        ((JComponent) box.getRenderer()).setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(PRIMARY_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(HOVER_COLOR);
            }

            public void mouseExited(MouseEvent evt) {
                btn.setBackground(PRIMARY_COLOR);
            }
        });
    }

    private void convertCurrency() {
        try {
            String from = (String) fromCurrencyBox.getSelectedItem();
            String to = (String) toCurrencyBox.getSelectedItem();
            String amountText = amountField.getText();

            if (amountText.isEmpty()) {
                shakeFrame();
                JOptionPane.showMessageDialog(frame, "Please enter an amount.", "Input Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            double amount = Double.parseDouble(amountText);

            convertButton.setEnabled(false);
            convertButton.setText("CONVERTING...");
            resultLabel.setText("Fetching rates...");

            new Thread(() -> {
                try {
                    double rate = service.getExchangeRate(from, to);
                    double result = amount * rate;

                    SwingUtilities.invokeLater(() -> {
                        DecimalFormat df = new DecimalFormat("#,##0.00");
                        resultLabel.setText(df.format(amount) + " " + from + " = " + df.format(result) + " " + to);
                        resultLabel.setForeground(new Color(34, 139, 34)); // Success Green
                        convertButton.setEnabled(true);
                        convertButton.setText("CONVERT CURRENCY");
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        resultLabel.setText("Error");
                        resultLabel.setForeground(Color.RED);
                        JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "API Error",
                                JOptionPane.ERROR_MESSAGE);
                        convertButton.setEnabled(true);
                        convertButton.setText("CONVERT CURRENCY");
                    });
                }
            }).start();

        } catch (NumberFormatException ex) {
            shakeFrame();
            JOptionPane.showMessageDialog(frame, "Invalid amount format.", "Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void shakeFrame() {
        final Point original = frame.getLocation();
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    SwingUtilities.invokeAndWait(() -> frame.setLocation(original.x + 5, original.y));
                    Thread.sleep(20);
                    SwingUtilities.invokeAndWait(() -> frame.setLocation(original.x - 5, original.y));
                    Thread.sleep(20);
                }
                SwingUtilities.invokeAndWait(() -> frame.setLocation(original));
            } catch (Exception ignored) {
            }
        }).start();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(CurrencyConverterApp::new);
    }
}
