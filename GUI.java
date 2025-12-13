import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class GUI extends JFrame implements ActionListener {
    
    private String userGuess;
    private JTextField userGuessInput;
    private JButton userGuessButton;
    private DefaultTableModel dtm;
    private JLabel guessLabel;
    private ArrayList<String> allGuesses;

    public GUI() {
        super("Hangman");

        allGuesses = new ArrayList<>();
        JPanel inputPanel = new JPanel(new FlowLayout()); // Use FlowLayout for simplicity

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        setSize(1000, 400); // Increased window size
        setLocationRelativeTo(null); // Center the window
        
        guessLabel = new JLabel("Enter Guess: ");

        userGuessButton = new JButton("Guess");
        userGuessButton.setPreferredSize(new Dimension(75,40));
        userGuessButton.addActionListener(this);
        
        userGuessInput = new JTextField(1);

        inputPanel.add(guessLabel);
        inputPanel.add(userGuessInput);
        inputPanel.add(userGuessButton);

        add(inputPanel, BorderLayout.NORTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == userGuessButton) {
            allGuesses.add(userGuessInput.getText().trim());
            userGuessInput.setText("");
        }
    }

    public static void main(String[] args) {
        new GUI();
    }
}

