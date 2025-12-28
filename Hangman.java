import java.util.*;

public class Hangman {
  private int count;
  private boolean correctGuess;
  Map<String, String> hints = new HashMap<>();
  private ArrayList<String> allGuesses;
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
      allGuesses = new ArrayList<>();

      hints.put("dog", "A common pet that barks.");
      hints.put("cat", "A common pet that meows.");
      hints.put("fish", "An animal that lives in water and swims.");
      hints.put("tree", "A tall plant with a trunk and leaves.");
      hints.put("house", "A place where people live.");
      hints.put("apple", "A fruit that can be red, green, or yellow.");
      hints.put("chair", "Furniture you sit on.");
      hints.put("phone", "A device used to call or text people.");
      hints.put("music", "Sounds you listen to, often with rhythm.");
      hints.put("pizza", "A popular food with cheese and toppings.");

      hints.put("computer", "An electronic device used to run programs.");
      hints.put("hamburger", "A sandwich with a patty, often with toppings.");
      hints.put("backpack", "A bag you carry on your back.");
      hints.put("notebook", "Pages bound together for writing notes.");
      hints.put("airplane", "A vehicle that flies in the sky.");
      hints.put("mountain", "A very large natural hill.");
      hints.put("calendar", "Shows days, weeks, and months.");
      hints.put("football", "A sport played with a ball and teams.");
      hints.put("language", "A system people use to communicate.");
      hints.put("birthday", "The day you were born, celebrated yearly.");

      hints.put("adventure", "An exciting experience or journey.");
      hints.put("chocolate", "A sweet treat made from cocoa.");
      hints.put("telescope", "A tool used to see far-away objects in space.");
      hints.put("microscope", "A tool used to see tiny objects.");
      hints.put("electricity", "Energy that powers lights and devices.");
      hints.put("programming", "Writing code to make software.");
      hints.put("algorithm", "Step-by-step instructions to solve a problem.");
      hints.put("javascript", "A programming language often used for websites.");
      hints.put("engineering", "Using science and math to build solutions.");
      hints.put("university", "A school for higher education after high school.");

      List<String> words = new ArrayList<>(hints.keySet());
      secretWord = words.get(new Random().nextInt(words.size()));
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
  public String getHint() {
    return hints.get(secretWord);
  }
  public String getSecretWord() {
    return secretWord;
  }
}
