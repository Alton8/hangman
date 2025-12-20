import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class GUI extends JFrame implements ActionListener {
    
    private JTextField userGuessInput;
    private JButton userGuessButton;
    private DefaultTableModel dtm;
    private JLabel guessLabel;
    private JLabel nameLabel;
    private JLabel revealedWordLabel;
    private JTextArea characterDrawing;
    private Hangman hangmanGame;
    private JLabel lettersGuessedLabel;
    private JButton retryButton;
    private JButton solveButton;

    public GUI() {
        super("Hangman");
        hangmanGame = new Hangman();
        hangmanGame.startGame();

        JPanel inputPanel = new JPanel(new FlowLayout()); // Use FlowLayout for simplicity
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

        
        guessLabel = new JLabel("Enter Guess: ");
        revealedWordLabel = new JLabel(hangmanGame.getRevealedWord());
        lettersGuessedLabel = new JLabel(hangmanGame.getAllGuessesString());
        
        characterDrawing = new JTextArea(hangmanGame.displayImage());
        characterDrawing.setAlignmentX(Component.CENTER_ALIGNMENT);
        characterDrawing.setEditable(false);

        solveButton = new JButton("Guess Full Word");
        solveButton.setPreferredSize(new Dimension(150,40));
        solveButton.addActionListener(this);

        userGuessButton = new JButton("Guess");
        userGuessButton.setPreferredSize(new Dimension(75,30));
        userGuessButton.addActionListener(this);
        
        retryButton = new JButton("Retry");
        retryButton.setPreferredSize(new Dimension(75,40));
        retryButton.addActionListener(this);
        retryButton.setVisible(false);

        userGuessInput = new JTextField(1);
        nameLabel = new JLabel("Welcome to Hangman!");


        inputPanel.add(guessLabel);
        inputPanel.add(userGuessInput);
        inputPanel.add(userGuessButton);
        inputPanel.add(solveButton);
        gamePanel.add(nameLabel);
        gamePanel.add(characterDrawing);
        gamePanel.add(revealedWordLabel);
        gamePanel.add(lettersGuessedLabel);
        gamePanel.add(retryButton);
        add(inputPanel, BorderLayout.NORTH);

        add(gamePanel);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        setLocationRelativeTo(null); // Center the window
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == userGuessButton) {
            enterGuess();
            updateScreen();
            if (hangmanGame.isCorrectGuess()) {
                endGame();
                JOptionPane.showMessageDialog(this, "Congratulations! You won! The word was " + hangmanGame.getSecretWord() + ".", "Notification", JOptionPane.PLAIN_MESSAGE);
            } else if (hangmanGame.getCount() == 6 && !hangmanGame.isCorrectGuess()) {
                endGame();
                JOptionPane.showMessageDialog(this, "You lost! The word was " + hangmanGame.getSecretWord() + ".", "Notification", JOptionPane.PLAIN_MESSAGE);
            }
        } else if (e.getSource() == retryButton) {
            restartGame();
        } else if (e.getSource() == solveButton) {
            solve();
        }
    }

    public void enterGuess() {
        boolean duplicateGuess = false;
        String userGuess = userGuessInput.getText().trim();
        if (userGuess.length() != 1 || !Character.isLetter(userGuess.charAt(0))) {
            JOptionPane.showMessageDialog(this, "Please enter a valid letter", "Input Error", JOptionPane.WARNING_MESSAGE);
        } else {
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
    }
    
    public void solve() {
        String userGuess = JOptionPane.showInputDialog("What is the word?");
        if (userGuess.equalsIgnoreCase(hangmanGame.getSecretWord())) {
            endGame();
            revealedWordLabel.setText(hangmanGame.getSecretWord());
            JOptionPane.showMessageDialog(this, "Congratulations! You won! The word was " + hangmanGame.getSecretWord() + ".", "Notification", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Wrong. Keep playing!", "Notification", JOptionPane.PLAIN_MESSAGE);
            hangmanGame.increaseCount();
            updateScreen();
        }
    }
    public void endGame() {
        userGuessInput.setVisible(false);
        userGuessButton.setVisible(false);
        guessLabel.setVisible(false);
        retryButton.setVisible(true);
        solveButton.setVisible(false);
    }

    public void restartGame() {
        hangmanGame = new Hangman();
        hangmanGame.startGame();
        userGuessInput.setVisible(true);
        lettersGuessedLabel.setText(hangmanGame.getAllGuessesString());
        characterDrawing.setText(hangmanGame.displayImage());
        revealedWordLabel.setText(hangmanGame.getRevealedWord());
        userGuessButton.setVisible(true);
        guessLabel.setVisible(true);
        retryButton.setVisible(false);
    }
    public void updateScreen() {
        revealedWordLabel.setText(hangmanGame.getRevealedWord());
        userGuessInput.setText("");
        characterDrawing.setText(hangmanGame.displayImage());
        lettersGuessedLabel.setText(hangmanGame.getAllGuessesString());
    }

    public static void main(String[] args) {
        new GUI();
    }
}

