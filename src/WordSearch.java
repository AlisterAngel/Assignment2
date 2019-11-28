import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordSearch {

    private char[][] wordSearch;
    private String[] wordList;

    public WordSearch(){
        int rows = getDimension("rows");
        int columns = getDimension("columns");
        wordList = new String[rows];
        wordSearch = new char[rows][columns];

        wordList = getWords(rows, columns);
        wordSearch = fillGrid(rows, columns);
        wordSearch = includeWords(columns);
    }

    /**
     * Adds the words from the list to the grid
     * @param columns
     * @return puzzle
     */
    public char[][] includeWords(int columns) {
        String[] randomWordList = randomOrderStringArray();
        char[][] puzzle = wordSearch;
        for(int i = 0; i < randomWordList.length; i++){
            int offset = randomNumberGenerator(columns - randomWordList[i].length());
            //System.out.println(offset);
            boolean reversed = randomBooleanGenerator();

        for(int k = 0; k < randomWordList[i].length(); k++) {
                if(!reversed){
                    puzzle[i][k + offset] = randomWordList[i].charAt(k);
                }else{
                    puzzle[i][randomWordList[i].length() - k - offset] = randomWordList[i].charAt(k);
                }
            }
        }
        return puzzle;
    }

    /**
     *  randomly shifts the strings in the array
     * @return the word list shuffled
     */
    public String[] randomOrderStringArray(){
        String[] tempWordList = wordList;
        for (int i=0; i<tempWordList.length; i++) {
            int randomPosition = randomNumberGenerator(tempWordList.length);
            String temp = tempWordList[i];
            tempWordList[i] = tempWordList[randomPosition];
            tempWordList[randomPosition] = temp;
        }
        return tempWordList;
    }

    /**
     *  Gives random number within a range
     * @param range
     * @return random number within range 0 to (requested range -1)
     */
    public int randomNumberGenerator(int range){
        Random randomNum = new Random();
        int random = randomNum.nextInt(range);
        //System.out.print(random);
        return random;
    }

    /**
     * Gives a random two choice answer
     * @return true or false
     */
    public boolean randomBooleanGenerator(){
        int random = randomNumberGenerator(2);
        boolean choice;
        if(random == 1){
            choice = false;
        }else{
            choice = true;
        }
        return choice;
    }

    /**
     * asks for a length for a dimension or size
     * @param axis
     * @return integer from 2-15
     */
    public int getDimension(String axis){
        int length = 0;
        while(length < 2 || length > 15) {
            System.out.println("Enter number of " + axis + " from 2-15:");
                length = scanInt();
        }
        return length;
    }

    /**
     * ask for words
     * @param width
     * @param length
     * @return array full of valid words
     */
    public String[] getWords(int width, int length){
        String[] collection = new String[length];
        for(int i = 0; i < width; i++) {
            boolean validWordCheck = false;
            while(!validWordCheck){
                System.out.print("Enter a word with less than or equal to " + length + " letters: ");
                collection[i] = scanString();
                validWordCheck = wordCheck(collection[i]);
                if(collection[i].length() > length){
                    validWordCheck = false;
                }
                if(!validWordCheck){
                    System.out.print("Input not valid, please enter valid word\n");
                }
            }
        }
        return collection;
    }

    /**
     * checks and sees if int has been entered
     * @return scans for valid int
     */
    public int scanInt(){
        Scanner input = new Scanner(System.in);
        int numberRequest = -1;
        try {
            numberRequest = input.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.print("Only integers are allowed\n");
        }
        input.nextLine();
        return numberRequest;
    }

    /**
     * Scan for a string
     * @return a valid string
     */
    public String scanString(){
        Scanner input = new Scanner(System.in);
        String stringRequest = input.nextLine();
        stringRequest = stringRequest.toUpperCase();
        return stringRequest;
    }

    /**
     * checks to see if String is a valid word
     * @param word
     * @return true if valid
     */
    public boolean wordCheck(String word){
        boolean check1 = specialCharacters(word);
        boolean check2 = numberCheck(word);
        if(check1||check2){
            return false;
        }
        return true;
    }

    /**
     * Checks to see if any characters are not letters or numbers
     * @param word
     * @return true if no special characters
     */
    public boolean specialCharacters(String word){
        Pattern specialCharactersList = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher checkSpecialCharacters = specialCharactersList.matcher(word);
        //System.out.print(checkSpecialCharacters + "\n");
        return checkSpecialCharacters.find();
    }

    /**
     * Checks to see if there is a number
     * @param word
     * @return flase if number exists
     */
    public boolean numberCheck(String word){
        Pattern numberList = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE);
        Matcher checkNumbers = numberList.matcher(word);
        return checkNumbers.find();
    }

    /**
     * fills grid with random letters
     * @param rows
     * @param columns
     * @return grid filled with random letters
     */
    public char[][] fillGrid(int rows, int columns){
        char[][] collection = new char[rows][columns];
        for(int i = 0; i < rows; i ++){
            for(int k = 0; k < columns; k ++){
                int set = randomNumberGenerator(26) + 65;
                collection[i][k] = (char)set;
            }
        }
        return collection;
    }

    /**
     * converts the wordsearch into a String
     * @return string
     */
    public String getWordSearchString(){
        int rows = wordSearch.length;
        int columns = 0;
        try {
            columns = wordSearch[0].length;
        }catch(java.lang.ArrayIndexOutOfBoundsException e){}
        String puzzleSheet = "";
        for(int i = 0; i < rows; i ++){
            for(int k = 0; k < columns; k ++){
                puzzleSheet += " " + wordSearch[i][k];
                if(wordSearch[0].length-1 == k){
                    puzzleSheet += "\n";
                }
            }
        }
        return puzzleSheet;
    }

    /**
     * Turns word list into a string
     * @return String
     */
    public String WordsString() {
        int rows = wordList.length;
        String wordset = "The words to find: \n";
        for(int i = 0; i < rows; i ++) {
            wordset += wordList[i] + "\n";
        }
        return wordset;
    }

    public void printToFile() {
        Formatter outputFile;
        try {
            outputFile = new Formatter("WordSearch.txt");
            outputFile.format(wordSearch + "\n");
            outputFile.format(WordsString() + "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
