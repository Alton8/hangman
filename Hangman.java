import java.util.ArrayList;
import java.util.Scanner;

public class Hangman {
    public Hangman() {
        Scanner keyboard = new Scanner(System.in);
        String[] hangmanPics = {
    """
      +---+
      |   |
          |
          |
          |
          |
    =========
    """,
    """
      +---+
      |   |
      O   |
          |
          |
          |
    =========
    """,
    """
      +---+
      |   |
      O   |
      |   |
          |
          |
    =========
    """,
    """
      +---+
      |   |
      O   |
     /|   |
          |
          |
    =========
    """,
    """
      +---+
      |   |
      O   |
     /|\\  |
          |
          |
    =========
    """,
    """
      +---+
      |   |
      O   |
     /|\\  |
     /    |
          |
    =========
    """,
    """
      +---+
      |   |
      O   |
     /|\\  |
     / \\  |
          |
    =========
    """
};
        int count = 0;
        boolean correctGuess = false;
        ArrayList<String> randomWords = new ArrayList<>();
        randomWords.add("dog");
        randomWords.add("testword");
        randomWords.add("hamburger");
        int randomIndex = (int)(Math.random() * randomWords.size());
        String word = randomWords.get(randomIndex);
        System.out.println(randomIndex);
        
        System.out.println("Welcome to Hangman!");
        System.out.println(hangmanPics[0]);
        String beginningString = "";
        for (int i=0; i < word.length(); i++) {
            System.out.print("_ ");
            beginningString += "_ ";
        }
        System.out.println();
        System.out.println("Guess the Word!");
        StringBuilder stringBuilder = new StringBuilder(beginningString);

        while (correctGuess != true && count < 6) {
          System.out.print("Your guess? ");
          String userGuess = keyboard.nextLine();
            if (userGuess.length() > 1 || Character.isDigit(userGuess.charAt(0))) {
              System.out.println("Error");
            } else {
              boolean correctLetter = false;
              for (int i=0; i < word.length(); i++) {
                if (userGuess.equals(Character.toString(word.charAt(i)))) {
                  if (i == word.length()-1) {
                    stringBuilder.setCharAt(stringBuilder.toString().length() - 2, word.charAt(i));
                  } else if (i == 0) {
                    stringBuilder.setCharAt(0, word.charAt(i));
                  } else if (i == 1) {
                    stringBuilder.setCharAt(2, word.charAt(i)); 
                  } else {
                     stringBuilder.setCharAt(((i+1)*2)-2, word.charAt(i));
                  }
                  correctLetter = true;
                } 
                
              }
              if (!correctLetter) {
                  count++;
              }
              if (!stringBuilder.toString().contains("_")) {
                correctGuess = true;
              }
        
            } 
          System.out.println(hangmanPics[count]);
          System.out.println(stringBuilder.toString());
        }

    }
    
    public static void main(String[] args) {
        new Hangman();
    }
}