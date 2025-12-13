import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class GUI extends JFrame implements ActionListener {
    
    private JTextField userGuessInput;
    private JButton userGuessButton;
    private DefaultTableModel dtm;
    private JLabel guessLabel;
    private final JLabel nameLabel = new JLabel("Welcome to Hangman!");
    private JLabel revealedWordLabel;
    private JTextArea characterDrawing;
    private ArrayList<String> allGuesses;
    private Hangman hangmanGame;

    public GUI() {
        super("Hangman");
        hangmanGame = new Hangman();
        hangmanGame.startGame();

        allGuesses = new ArrayList<>();
        JPanel inputPanel = new JPanel(new FlowLayout()); // Use FlowLayout for simplicity
        JPanel gamePanel = new JPanel(new FlowLayout());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        setSize(1000, 400); // Increased window size
        setLocationRelativeTo(null); // Center the window
        
        guessLabel = new JLabel("Enter Guess: ");
        revealedWordLabel = new JLabel(hangmanGame.getRevealedWord());
        characterDrawing = new JTextArea(hangmanGame.displayImage());
        userGuessButton = new JButton("Guess");
        userGuessButton.setPreferredSize(new Dimension(75,40));
        userGuessButton.addActionListener(this);
        
        userGuessInput = new JTextField(1);

        inputPanel.add(guessLabel);
        inputPanel.add(userGuessInput);
        inputPanel.add(userGuessButton);
        
        gamePanel.add(nameLabel, BorderLayout.NORTH);
        gamePanel.add(characterDrawing, BorderLayout.SOUTH);
        gamePanel.add(revealedWordLabel);
        gamePanel.setPreferredSize(new Dimension(2,1000));

        add(inputPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == userGuessButton) {
            enterGuess();
            updateScreen();
        }
    }

    public void enterGuess() {
        String userGuess = userGuessInput.getText().trim();
        if (userGuess.length() > 1 || Character.isDigit(userGuess.charAt(0))) {
            JOptionPane.showMessageDialog(this, "Please enter a valid letter", "Input Error", JOptionPane.WARNING_MESSAGE);
        } else {
            hangmanGame.enterGuess(userGuess);
            allGuesses.add(userGuess);
        }
    }
    
    public void updateScreen() {
        revealedWordLabel.setText(hangmanGame.getRevealedWord());
        userGuessInput.setText("");
        characterDrawing.setText(hangmanGame.displayImage());
    }
    public static void main(String[] args) {
        new GUI();
    }
}

