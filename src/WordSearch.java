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
        wordSearch = includeWords(rows, columns);
    }

    public char[][] includeWords(int rows, int columns) {
        String[] randomWordList = randomOrderStringArray();
        char[][] puzzle = char[rows][columns];
        for(int i = 0; i < randomWordList.length; i++){
            int random = randomNumberGenerator(columns - randomWordList[i].length());
            for(int k = 0; k < randomWordList[i].length(); k++) {

            }
        }
        return puzzle;
    }

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

    public int randomNumberGenerator(int range){
        Random rgen = new Random();
        int random = rgen.nextInt(range);
        return random;
    }

    public int getDimension(String axis){
        int length = 0;
        while(length >= 2 && length <= 15) {
            System.out.println("Enter number of " + axis + " from 2-15:");
                length = scanInt();
        }
        return length;
    }

    public String[] getWords(int width, int length){
        String[] collection = new String[length];
        for(int i = 0; i < width; i++) {
            boolean validWordCheck = false;
            while(!validWordCheck){
                System.out.print("Enter a word with less than " + length + " letters: ");
                collection[i] = scanString();
                collection[i] = collection[i].toUpperCase();
                validWordCheck = wordCheck(collection[i]);
                if(collection[i].length() > length - 1){
                    validWordCheck = false;
                }
                if(!validWordCheck){
                    System.out.print("Input not valid, please enter valid word\n");
                }
            }
        }
        return collection;
    }
    
    public int scanInt(){
        Scanner input = new Scanner(System.in);
        int numberRequest = -1;
        try {
            numberRequest = input.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.printf("Only integers are allowed\n");
        }
        return numberRequest;
    }

    public String scanString(){
        Scanner input = new Scanner(System.in);
        String numberRequest = input.nextLine();
        return numberRequest;
    }
    
    public boolean wordCheck(String word){
        boolean check1 = specialCharacters(word);
        boolean check2 = numberCheck(word);
        if(check1||check2){
            return false;
        }
        return true;
    }

    public boolean specialCharacters(String word){
        Pattern specialCharactersList = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher checkSpecialCharacters = specialCharactersList.matcher(word);

        return checkSpecialCharacters.find();
    }

    public boolean numberCheck(String word){
        Pattern numberList = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE);
        Matcher checkNumbers = numberList.matcher(word);

        return checkNumbers.find();
    }

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

    public String getWordSearchString(){
        int rows = wordSearch.length;
        int columns = wordSearch[0].length;
        String puzzleSheet = "";
        for(int i = 0; i < rows -1; i ++){
            for(int k = 0; k < columns -1; k ++){
                puzzleSheet += " " + wordList[i].substring(k);
                if(wordSearch[0].length-1 == k){
                    puzzleSheet += "\n";
                }
            }
        }
        return puzzleSheet;
    }

    public String WordsString() {
        int rows = wordList.length;
        String wordset = "The words to find: \n";
        for(int i = 0; i < rows -1; i ++) {
            wordset += wordList[i] + "\n";
        }
        return wordset;
    }

}
