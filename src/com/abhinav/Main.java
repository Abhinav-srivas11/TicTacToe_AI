package com.abhinav;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    static int toFinishCounter = 0;
    static Scanner sc = new Scanner(System.in);
    static boolean legalMoveMade = false;
    static HashMap<Character, Integer> scores = new HashMap<>();
    static Character  inputAI = ' ';
    static Character  human = ' ';

    //variable to end the game for the purpose of this exercise only
//    static int closeGame = 0;
    static boolean gameEnds = false;
    static Character[][] gameArray = new Character[3][3];
    static boolean computerTurn = false;
    public static void main(String[] args) {
        scores.put('X', 10);
        scores.put('O', -10);
        scores.put('D', 0);
        //user can input a string that can make X or O win immediately.Hence check for that also.
        boolean exitFlag = false;
        while(!exitFlag) {
            System.out.print("Input command: ");
            toFinishCounter = 0;
            computerTurn = false;
            String menu = sc.nextLine();
            String[] menuInput = menu.split(" ");
            if(menuInput[0].equals("exit")) {
                exitFlag = true;
            } else if (menuInput[0].equals("start")) {
                if (menuInput[1].equals("user") && (menuInput[2].equals("easy") ||menuInput[2].equals("hard") || menuInput[2].equals("medium"))) {
                    //fill the initial area for the game with blanks
                    fillGameArea(gameArray);
                    inputAI = 'O';
                    while ((toFinishCounter != 9)) {
                        if (computerTurn) {
                            System.out.println("Making move level \""+ menuInput[2] + "\"");
//                            gameEnds = fillGameArea(gameArray, computerMoves(), "O");
                            //legal move variable will be updated by fillGameArea method
                            //this is to make sure that computer gets another turn incase a legal move is not made by it
                            if (fillGameArea(gameArray, computerMoves(gameArray, menuInput[2]), inputAI)) {
                                computerTurn = false;
                            }
                        } else {
                            if (userInput('X')) {
                                computerTurn = true;
                            }
                        }
                        Character s = findWhoIsWinning(gameArray);
                        if (s == 'X' || s == 'O') {
                            System.out.println(s + " wins");
                            break;
                        } else if (s == 'D'){
                            System.out.println("Draw");
                            // here break is not necessary as draw will occur only when all blocks are full
                        }
                    }
                } else if (menuInput[2].equals("user") && (menuInput[1].equals("hard") || menuInput[1].equals("easy") || menuInput[1].equals("medium"))) {
                    fillGameArea(gameArray);
                    computerTurn = true;
                    inputAI = 'X';
                    while ((!gameEnds && toFinishCounter != 9)) {
                        if (computerTurn) {
                            System.out.println("Making move level \""+ menuInput[1] + "\"");
//                            gameEnds = fillGameArea(gameArray, computerMoves(), "O");
                            //legal move variable will be updated by fillGameArea method
                            //this is to make sure that computer gets another turn incase a legal move is not made by it
                            if (fillGameArea(gameArray, computerMoves(gameArray, menuInput[1]), inputAI)) {
                                computerTurn = false;
                            }
                        } else {
                            if (userInput('O')) {
                                computerTurn = true;
                            }
                        }
                        Character s = findWhoIsWinning(gameArray);
                        if (s == 'X' || s == 'O') {
                            System.out.println(s + " wins");
                            break;
                        } else if (s == 'D'){
                            System.out.println("Draw");
                            // here break is not necessary as draw will occur only when all blocks are full
                        }
                    }
                }else if (menuInput[1].equals("user") && menuInput[2].equals("user")) {
                    fillGameArea(gameArray);
                    boolean user2Turn = false;
                    while ((!gameEnds && toFinishCounter != 9)) {
                        if (user2Turn) {
                            if (userInput('O')) {
                                user2Turn = false;
                            }
                        } else {
                            if (userInput('X')) {
                                user2Turn = true;
                            }
                        }
                        Character s = findWhoIsWinning(gameArray);
                        if ( s == 'X' || s == 'O') {
                            System.out.println(s + " wins");
                            break;
                        } else if (s == 'D'){
                            System.out.println("Draw");
                            // here break is not necessary as draw will occur only when all blocks are full
                        }
                    }
                } else if ((menuInput[1].equals("easy") || menuInput[1].equals("hard") || menuInput[1].equals("medium")) && (menuInput[2].equals("easy") || menuInput[2].equals("hard") || menuInput[2].equals("medium"))) {
                    fillGameArea(gameArray);
                    boolean comp2Turn = false;
                    // we can assign inputAI variable inside each turn so that it will be updated automatically
                    while ((!gameEnds && toFinishCounter != 9)) {
                        if (comp2Turn) {
                            System.out.println("Making move level \"" + menuInput[2] + "\"");
                            inputAI = 'O';
                            //legal move variable will be updated by fillGameArea method
                            //this is to make sure that computer gets another turn incase a legal move is not made by it
                            if (fillGameArea(gameArray, computerMoves(gameArray, menuInput[1]), inputAI)) {
                                comp2Turn = false;
                            }
                        } else {
                            System.out.println("Making move level \"" + menuInput[1] + "\"");
                            inputAI = 'X';
                            //legal move variable will be updated by fillGameArea method
                            //this is to make sure that computer gets another turn incase a legal move is not made by it
                            if (fillGameArea(gameArray, computerMoves(gameArray, menuInput[1]), inputAI)) {
                                comp2Turn = true;
                            }
                        }
                        Character s = findWhoIsWinning(gameArray);
                        if ( s == 'X' || s == 'O') {
                            System.out.println(s + " wins");
                            break;
                        } else if (s == 'D'){
                            System.out.println("Draw");
                            // here break is not necessary as draw will occur only when all blocks are full
                        }
                    }
                }
            }else {
                System.out.println("Bad parameters!");
            }
        }
    }
/*-----------------------------------------------------------------------------------------------------------------
  End of Main
  start of defining other functions
 -----------------------------------------------------------------------------------------------------------------*/
//    private static void countOfFilledCells(String input) {
//        switch (input) {
//            case "X" :
//                countOfFilledCellsArr[0]++;
//                break;
//            case "O" :
//                countOfFilledCellsArr[1]++;
//                break;
//        }
//    }

    private static boolean userInput(Character xORo) {
        int[] input = new int[2];
        human = xORo;
        System.out.print("Enter the coordinates: ");
        String inputString = sc.nextLine();
        if (inputString.matches("[0-9\\s]+")) {
            String[] inputStr = inputString.split(" ");
            input[0] = Integer.parseInt(inputStr[0]);
            input[1] = Integer.parseInt(inputStr[1]);
//            gameEnds = fillGameArea(gameArray, input, xORo);
//            if(fillGameArea(gameArray, input, xORo)) {
//                computerTurn = true;
//            }
            return fillGameArea(gameArray, input, xORo);
        } else {
            System.out.println("You should enter numbers!");
            return false;
        }
    }

    //This method will fill the game area using the very first input line from the user.
    // this will begin the game
    private static void fillGameArea(Character[][] gameArray) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameArray[i][j] = ' ';
            }
        }
        printGameArray(gameArray);
    }


    private static boolean fillGameArea(Character[][] gameArray, int[] newIndex, Character inputWhat) {
        if((newIndex[0] < 4 && newIndex[0] >= 1) && (newIndex[1] < 4 && newIndex[1] >= 1)) {
            int i = 3 - newIndex[1];
            int j = newIndex[0] - 1;
            if(gameArray[i][j] == ' ') {
                //update the element at new index
                gameArray[i][j] = inputWhat;
                toFinishCounter++;
                legalMoveMade = true;
                //print the new updated game area
                printGameArray(gameArray);
            } else {
                System.out.println("This cell is occupied! Choose another one!");
                legalMoveMade = false;
            }
        } else {
            System.out.println("Coordinates should be from 1 to 3!");
            legalMoveMade = false;
            return legalMoveMade;
        }

        return legalMoveMade;
    }
    private static void printGameArray(Character[][] gameArray) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(gameArray[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");
        //print will only be called when a valid move has been made
        //update the close game counter it print function for better control over end of the game.
//        closeGame++;
    }
    //----------------------------------------------------------------------------------------------------------------
    private static Character findWhoIsWinning(Character[][] gameArray) {
        //check row elements
        for (int i = 0; i < 3; i++) {
            if (equalsAll(gameArray[i][0], gameArray[i][1], gameArray[i][2])) {
                return gameArray[i][0];
            }
        }

        //check column elements
        for (int j = 0; j < 3; j++) {
            if (equalsAll(gameArray[0][j], gameArray[1][j], gameArray[2][j])) {
                return gameArray[0][j];
            }
        }

        //check diagonal elements part 1
        if (equalsAll(gameArray[0][0], gameArray[1][1], gameArray[2][2])) {
            return gameArray[0][0];
        }

        //check diagonal elements part 2
        if (equalsAll(gameArray[0][2], gameArray[1][1], gameArray[2][0])) {
            return gameArray[0][2];
        }

        //check if we have no matching elements then it should be a draw
        if (toFinishCounter == 9) {
            return 'D';
        }
        return ' ';
    }

    private static int[] computerMoves(Character[][] gameArray, String level) {
        int[] inputArr = new int[2];
        switch (level) {
            case "medium" :
                return isUserGoingToWin(gameArray);
            case "hard" :
                return bestMove(gameArray);
            default:
                inputArr[0] = (int) (Math.random() * (4));
                inputArr[1] = (int) (Math.random() * (4));
                return inputArr;
        }
    }


    private static int[] isUserGoingToWin(Character[][] gameArray) {
        int[] inputArr = new int[2];
        //check rows
        for (int i = 0; i < 3; i++) {
            int j =0;
            if (equals2(gameArray[i][j], gameArray[i][j + 1]) ) {
                //this break is being used to avoid a forever running code
                //if this break were not exisiting then the code would run forever as it would return same value
                //even if there was an element present at given location
                if(gameArray[i][j+2] != ' ') break;
                return new int[] {3, 3 - i};
            } else if (equals2(gameArray[i][j + 1], gameArray[i][j + 2])) {
                if(gameArray[i][j] != ' ') break;
                return new int[] {1, 3 - i};
            }
        }

        //check column elements
        for (int j = 0; j < 3; j++) {
            int i = 0;
            if (equals2(gameArray[i][j], gameArray[i + 1][j]) ) {
                if(gameArray[i + 2][j] != ' ') break;
                return new int[] {j + 1, 1};
            } else if (equals2(gameArray[i + 1][j], gameArray[i + 2][j])) {
                if(gameArray[i][j] != ' ') break;
                return new int[] {j + 1 , 3};
            }
        }
        //check diagonal elements part 1
        if (equals2(gameArray[0][0], gameArray[1][1])){
            if(gameArray[2][2] == ' ') {
                return new int[]{3, 1};
            }
        } else if (equals2(gameArray[1][1], gameArray[2][2])) {
            if(gameArray[0][0] == ' ') {
                return new int[]{1, 3};
            }
        }
        //check diagonal elements part 2
        if (equals2(gameArray[0][2], gameArray[1][1])) {
            if(gameArray[2][0] == ' ') {
                return new int[]{1, 1};
            }
        } else if(equals2(gameArray[1][1], gameArray[2][0])) {
            if(gameArray[0][2] == ' ') {
                return new int[]{3, 3};
            }
        }

        inputArr[0] = (int) (Math.random() * (3) + 1);
        inputArr[1] = (int) (Math.random() * (3) + 1);
        return inputArr;
    }
    private static int[] bestMove(Character[][] gameArray) {
        // AI to make its turn
        int bestScore = Integer.MIN_VALUE;
        int[] move = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Is the spot available?
                if (gameArray[i][j] == ' ') {
                    gameArray[i][j] = inputAI;
                    int score = miniMax(gameArray, 0, false);
                    gameArray[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        move = new int[]{j + 1, 3 - i};
                    }
                }
            }
        }
        return move;
    }
    private static int miniMax(Character[][] gameArray, int depth, boolean isMaximizing) {
        Character result = findWhoIsWinning(gameArray);
        if(result != ' ') {
            return scores.get(result);
        }
        if(isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    //is the spot available in the array
                    if(gameArray[i][j] == ' ') {
                        gameArray[i][j] = inputAI;
                        int score = miniMax(gameArray, depth + 1, false);
                        //this is false here because the next turn would be of the opponent which
                        //will be minimizing to this user's winning
                        gameArray[i][j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(gameArray[i][j] == ' ') {
                        gameArray[i][j] = human;
                        int score = miniMax(gameArray, depth + 1, true);
                        //this is true here because the next turn would be of the opponent(AI) which
                        //will be maximizing to this AI's winning
                        gameArray[i][j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    private static boolean equalsAll(Character a,Character b,Character c) {
        return a==b && b==c && a != ' ';
    }
    private static boolean equals2(Character a,Character b) {
        return a == b  && a != ' ';
    }

}