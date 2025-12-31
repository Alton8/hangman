import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class GUI extends JFrame implements ActionListener {

    private JTextField userGuessInput;
    private JButton userGuessButton;
    private JLabel guessLabel;
    private JLabel nameLabel;
    private JLabel revealedWordLabel;
    private JTextPane characterDrawing; // JTextPane allows styled/centered text (used for ASCII art)
    private Hangman hangmanGame;         // Handles game logic (word, guesses, win/loss)
    private JLabel lettersGuessedLabel;
    private JButton retryButton;
    private JButton solveButton;
    private JButton hintButton;

    public GUI() {
        super("Hangman");

        hangmanGame = new Hangman();
        hangmanGame.startGame(); // Initializes secret word and game state

        JPanel inputPanel = new JPanel(new FlowLayout()); // Top panel for user input
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS)); // Vertical layout

        guessLabel = new JLabel("Enter Guess: ");
        revealedWordLabel = new JLabel(hangmanGame.getRevealedWord());
        lettersGuessedLabel = new JLabel(hangmanGame.getAllGuessesString());

        characterDrawing = new JTextPane();
        characterDrawing.setEditable(false);

        // Centers text inside the JTextPane (needed for ASCII hangman art)
        StyledDocument doc = characterDrawing.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        characterDrawing.setText(hangmanGame.displayImage());
        characterDrawing.setAlignmentX(Component.CENTER_ALIGNMENT);

        solveButton = new JButton("Guess Full Word");
        solveButton.setPreferredSize(new Dimension(150,40));
        solveButton.addActionListener(this);

        userGuessButton = new JButton("Guess");
        userGuessButton.setPreferredSize(new Dimension(75,30));
        userGuessButton.addActionListener(this);

        retryButton = new JButton("Retry");
        retryButton.setPreferredSize(new Dimension(75,40));
        retryButton.addActionListener(this);
        retryButton.setVisible(false); // Only shown after game ends

        hintButton = new JButton("Hint");
        hintButton.setPreferredSize(new Dimension(150,40));
        hintButton.addActionListener(this);
        hintButton.setVisible(false); // Appears later in the game

        userGuessInput = new JTextField(1); // Limit input to one character
        nameLabel = new JLabel("Welcome to Hangman!");

        // BoxLayout does not auto-center components, so this is required
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        characterDrawing.setAlignmentX(Component.CENTER_ALIGNMENT);
        revealedWordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        lettersGuessedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        retryButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputPanel.add(guessLabel);
        inputPanel.add(userGuessInput);
        inputPanel.add(userGuessButton);
        inputPanel.add(solveButton);
        inputPanel.add(hintButton);

        gamePanel.add(nameLabel);
        gamePanel.add(characterDrawing);
        gamePanel.add(revealedWordLabel);
        gamePanel.add(lettersGuessedLabel);
        gamePanel.add(retryButton);

        add(inputPanel, BorderLayout.NORTH);
        add(gamePanel);

        pack(); // Sizes frame based on its components
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window on screen
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // All button clicks are handled here
        if (e.getSource() == userGuessButton) {
            enterGuess();
            updateScreen();

            // Win condition
            if (hangmanGame.isCorrectGuess()) {
                endGame();
                JOptionPane.showMessageDialog(
                        this,
                        "Congratulations! You won! The word was " +
                        hangmanGame.getSecretWord() +
                        ". You used " + hangmanGame.getGuessCount() + " guesses!",
                        "Notification",
                        JOptionPane.PLAIN_MESSAGE
                );
            }
            // Loss condition (max image index reached)
            else if (hangmanGame.getImageIndex() == 6 && !hangmanGame.isCorrectGuess()) {
                endGame();
                JOptionPane.showMessageDialog(
                        this,
                        "You lost! The word was " + hangmanGame.getSecretWord() + ".",
                        "Notification",
                        JOptionPane.PLAIN_MESSAGE
                );
            }
        }
        else if (e.getSource() == retryButton) {
            restartGame();
        }
        else if (e.getSource() == solveButton) {
            solve();
        }
        else if (e.getSource() == hintButton) {
            JOptionPane.showMessageDialog(this, hangmanGame.getHint(), "Hint", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void enterGuess() {
        boolean duplicateGuess = false;
        String userGuess = userGuessInput.getText().trim();

        // Input validation (must be a single letter)
        if (userGuess.length() != 1 || !Character.isLetter(userGuess.charAt(0))) {
            JOptionPane.showMessageDialog(this, "Please enter a valid letter", "Input Error", JOptionPane.WARNING_MESSAGE);
        } else {
            // Check for repeated guesses
            for (String guess : hangmanGame.getAllGuesses()) {
                if (guess.equalsIgnoreCase(userGuess)) {
                    JOptionPane.showMessageDialog(this, "You already guessed this.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    userGuessInput.setText("");
                    duplicateGuess = true;
                    break;
                }
            }
            if (!duplicateGuess) {
                hangmanGame.enterGuess(userGuess);
            }
        }

        // Reveal hint button once the player is close to losing
        if (hangmanGame.getImageIndex() == 4) {
            hintButton.setVisible(true);
        }
    }

    public void solve() {
        // Prompts user to guess the entire word
        String userGuess = JOptionPane.showInputDialog("What is the word?");

        if (userGuess.equalsIgnoreCase(hangmanGame.getSecretWord())) {
            endGame();
            revealedWordLabel.setText(hangmanGame.getSecretWord());
            JOptionPane.showMessageDialog(this, "Congratulations! You won!", "Notification", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Wrong. Keep playing!", "Notification", JOptionPane.PLAIN_MESSAGE);
            hangmanGame.enterGuess(userGuess); // Wrong full-word guess still counts
            updateScreen();
        }
    }

    public void endGame() {
        // Disable gameplay controls and show retry button
        userGuessInput.setVisible(false);
        userGuessButton.setVisible(false);
        guessLabel.setVisible(false);
        retryButton.setVisible(true);
        solveButton.setVisible(false);
        hintButton.setVisible(false);
    }

    public void restartGame() {
        // Reset game logic and restore UI state
        hangmanGame = new Hangman();
        hangmanGame.startGame();

        userGuessInput.setVisible(true);
        lettersGuessedLabel.setText(hangmanGame.getAllGuessesString());
        characterDrawing.setText(hangmanGame.displayImage());
        revealedWordLabel.setText(hangmanGame.getRevealedWord());
        userGuessButton.setVisible(true);
        solveButton.setVisible(true);
        guessLabel.setVisible(true);
        retryButton.setVisible(false);
    }

    public void updateScreen() {
        // Sync UI with current game state
        revealedWordLabel.setText(hangmanGame.getRevealedWord());
        userGuessInput.setText("");
        characterDrawing.setText(hangmanGame.displayImage());
        lettersGuessedLabel.setText(hangmanGame.getAllGuessesString());
    }

    public static void main(String[] args) {
        new GUI(); // Launch application
    }
}
