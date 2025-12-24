import java.util.ArrayList;

public class Hangman {
  private int count;
  private boolean correctGuess;
  private ArrayList<String> randomWords;
  private ArrayList<String> allGuesses;
  private int randomIndex;
  private String secretWord;
  private StringBuilder stringBuilder;
  private static final String[] hangmanPics = {
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

  public Hangman() {
      count = 0;
      correctGuess = false;
      randomWords = new ArrayList<>();
      allGuesses = new ArrayList<>();
      randomWords.add("dog");
      randomWords.add("testword");
      randomWords.add("hamburger");
      randomIndex = (int)(Math.random() * randomWords.size());
      secretWord = randomWords.get(randomIndex);
  }

  public void startGame() {
      String beginningString = "";
      for (int i=0; i < secretWord.length(); i++) {
          beginningString += "_ ";
      }
      stringBuilder = new StringBuilder(beginningString);

     
  }

  public void enterGuess(String userGuess) {
    boolean correctLetter = false;
    for (int i=0; i < secretWord.length(); i++) {
      if (userGuess.equalsIgnoreCase(Character.toString(secretWord.charAt(i)))) {
        if (i == secretWord.length()-1) {
          stringBuilder.setCharAt(stringBuilder.toString().length() - 2, secretWord.charAt(i));
        } else if (i == 0) {
          stringBuilder.setCharAt(0, secretWord.charAt(i));
        } else if (i == 1) {
          stringBuilder.setCharAt(2, secretWord.charAt(i)); 
        } else {
            stringBuilder.setCharAt(((i+1)*2)-2, secretWord.charAt(i));
        }
        correctLetter = true;
      } 
      
    }
    allGuesses.add(userGuess);
    if (!correctLetter) {
        count++;
    }
    if (!stringBuilder.toString().contains("_")) {
      correctGuess = true;
    }
  }

  public boolean isCorrectGuess() {
    return correctGuess;
  }

  public ArrayList<String> getAllGuesses() {
    return allGuesses;
  }

  public String getAllGuessesString() {
    String userGuesses = "Letters Guessed: ";
    for (String s : allGuesses) {
      userGuesses += s + " ";
    }
    return userGuesses;
  }
  public void increaseCount() {
    count++;
  }
  public int getCount() {
    return count;
  }
  public String getRevealedWord() {
      return stringBuilder.toString();
  }
  public String displayImage() {
      return hangmanPics[count];
  }

  public String getSecretWord() {
    return secretWord;
  }

}