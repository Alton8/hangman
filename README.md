# Hangman Game (Java Swing)

A graphical implementation of the classic Hangman game built using **Java Swing**.  
The game randomly selects a word with a corresponding hint, tracks letter guesses, visually displays the hangman progress, and allows players to either guess letters or solve the full word.

## Features
- Graphical user interface using **Java Swing**
- Random word selection with built-in hints
- Letter-by-letter guessing
- Full word guess option
- Hint button to assist the player
- Visual hangman drawing that updates after each incorrect guess
- Tracks all previously guessed letters
- Retry button to restart the game after win or loss

## Gameplay
- Players guess one letter at a time
- Incorrect guesses advance the hangman drawing
- Players can choose to guess the entire word at any time
- The game ends when the word is fully revealed or the hangman is completed

## Technologies Used
- Java
- Java Swing (GUI)
- `ArrayList`, `HashMap`
- Event handling (`ActionListener`)
- Object-oriented programming principles

## Project Structure
- `GUI.java` — Handles the graphical interface and user interactions
- `Hangman.java` — Contains game logic, word selection, hints, and hangman states

## How to Run
1. Clone the repository
2. Compile the files:
   ```bash
   javac GUI.java Hangman.java
