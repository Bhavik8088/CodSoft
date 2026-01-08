import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGame extends JFrame {
    private int targetNumber;
    private int maxAttempts = 10;
    private int attemptsUsed;
    private int score;
    private int round;
    private final int RANGE_MIN = 1;
    private final int RANGE_MAX = 100;

    private JLabel titleLabel;
    private JLabel promptLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JLabel feedbackLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private JButton nextRoundButton;
    private JTextArea gameLog;

    public NumberGame() {
        setTitle("Number Guessing Game");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Initialize State
        score = 0;
        round = 0;
        
        // --- Components ---
        
        // Top Panel: Title and Score
        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        titleLabel = new JLabel("Number Guessing Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 150));
        
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        scoreLabel = new JLabel("Score (Rounds Won): 0");
        JLabel roundLabel = new JLabel("Round: 1"); // Will update
        statsPanel.add(scoreLabel);
        statsPanel.add(roundLabel);

        topPanel.add(titleLabel);
        topPanel.add(statsPanel);
        
        // Center Panel: Game Interaction
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        promptLabel = new JLabel("Guess a number between " + RANGE_MIN + " and " + RANGE_MAX + ":");
        promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        promptLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        guessField = new JTextField();
        guessField.setMaximumSize(new Dimension(200, 40));
        guessField.setFont(new Font("Arial", Font.BOLD, 18));
        guessField.setHorizontalAlignment(JTextField.CENTER);
        
        guessButton = new JButton("Guess");
        guessButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        guessButton.setFont(new Font("Arial", Font.BOLD, 14));
        guessButton.setBackground(new Color(70, 130, 180));
        guessButton.setForeground(Color.WHITE);
        
        feedbackLabel = new JLabel("Enter a number to start!");
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        feedbackLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        feedbackLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        attemptsLabel = new JLabel("Attempts left: " + maxAttempts);
        attemptsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        attemptsLabel.setForeground(Color.RED);

        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(promptLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(guessField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(guessButton);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(feedbackLabel);
        centerPanel.add(Box.createVerticalStrut(5));
        centerPanel.add(attemptsLabel);

        // Bottom Panel: Log and Controls
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        gameLog = new JTextArea(8, 30);
        gameLog.setEditable(false);
        gameLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(gameLog);
        
        nextRoundButton = new JButton("Next Round");
        nextRoundButton.setEnabled(false);
        nextRoundButton.setFont(new Font("Arial", Font.BOLD, 14));

        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        bottomPanel.add(nextRoundButton, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Event Listeners ---
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });
        
        // checkGuess on Enter key in text field
        guessField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        nextRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewRound();
            }
        });

        startNewRound();        
    }

    private void startNewRound() {
        Random rand = new Random();
        targetNumber = rand.nextInt(RANGE_MAX - RANGE_MIN + 1) + RANGE_MIN;
        attemptsUsed = 0;
        round++;
        
        guessField.setText("");
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
        nextRoundButton.setEnabled(false);
        feedbackLabel.setText("New round started! Good luck.");
        attemptsLabel.setText("Attempts left: " + maxAttempts);
        
        // Update stats panel
        ((JLabel)((JPanel)((JPanel)getContentPane().getComponent(0)).getComponent(1)).getComponent(1)).setText("Round: " + round);
        
        log("--- Round " + round + " Started ---");
    }

    private void checkGuess() {
        String input = guessField.getText();
        try {
            int guess = Integer.parseInt(input);
            attemptsUsed++;
            int attemptsLeft = maxAttempts - attemptsUsed;
            
            attemptsLabel.setText("Attempts left: " + attemptsLeft);

            if (guess == targetNumber) {
                handleWin();
            } else if (attemptsLeft <= 0) {
                handleLoss();
            } else if (guess < targetNumber) {
                feedbackLabel.setText("Too Low! Try higher.");
                log("Guess: " + guess + " (Too Low)");
            } else {
                feedbackLabel.setText("Too High! Try lower.");
                log("Guess: " + guess + " (Too High)");
            }
            
            guessField.setText("");
            guessField.requestFocus();
            
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Invalid input! Please enter a number.");
        }
    }

    private void handleWin() {
        score++;
        scoreLabel.setText("Score (Rounds Won): " + score);
        feedbackLabel.setText("CORRECT! The number was " + targetNumber);
        feedbackLabel.setForeground(new Color(0, 150, 0));
        log("WIN! Guessed " + targetNumber + " in " + attemptsUsed + " attempts.");
        endRound();
    }

    private void handleLoss() {
        feedbackLabel.setText("Game Over! The number was " + targetNumber);
        feedbackLabel.setForeground(Color.RED);
        log("LOSS! Out of attempts. Number was " + targetNumber + ".");
        endRound();
    }

    private void endRound() {
        guessField.setEnabled(false);
        guessButton.setEnabled(false);
        nextRoundButton.setEnabled(true);
        nextRoundButton.requestFocus();
    }

    private void log(String message) {
        gameLog.append(message + "\n");
        gameLog.setCaretPosition(gameLog.getDocument().getLength());
    }

    public static void main(String[] args) {
        // Run on Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGame().setVisible(true);
            }
        });
    }
}
