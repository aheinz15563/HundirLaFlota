import java.util.InputMismatchException;
import java.util.Random;

public class HundirLaFlota {

    private final int GAME_LENGTH = 10;
    private final int GAME_WIDTH = 10;
    private final char [][] boatsPlayer1;
    private final char [][] boatsPlayer2;
    private final char [][] boatsAttackedPlayer1;
    private final char [][] boatsAttackedPlayer2;
    private final Print print;
    private final UserInterface userInterface;
    private int [] rowColumn;
    private int turn;
    private final Random random;
    private final char [] boatState;
    private final char failedIcon;
    private boolean attackHit;

    public HundirLaFlota(){
        boatsPlayer1 = new char[GAME_LENGTH][GAME_WIDTH];
        boatsPlayer2 = new char[GAME_LENGTH][GAME_WIDTH];
        boatsAttackedPlayer1 = new char[GAME_LENGTH][GAME_WIDTH];
        boatsAttackedPlayer2 = new char[GAME_LENGTH][GAME_WIDTH];
        userInterface = new UserInterface();
        print = new Print();
        rowColumn = new int[2];
        turn = 0;
        random = new Random();
        boatState = new char[]{'O', 'X'};
        failedIcon = '.';
        generateBoats(boatsPlayer1);
        generateBoats(boatsPlayer2);
    }

    public void start (){
        while ( gameRunning(currentPlayerTable()) ){
            print.printGame(currentPlayerTable(), currentPlayerEnemyBoatsAttacked());
            print.printPlayerTurn(currentPlayerToString());
            checkAndAssignRowAndColumn();
            attackHit = attackHit();
            setIconAttackedRowAndColumn(rowColumn);
            setTurn();
        }
    }
    private boolean attackHit (){
        return nextPlayerTable()[rowColumn[0]][rowColumn[1]] == boatState[0];
    }

    private void setTurn(){
        if (!attackHit){
            turn++;
        }
    }
    private void checkAndAssignRowAndColumn (){
        String [] inputRowCol;
        try{
            inputRowCol = userInterface.getRowColumn();
            rowColumn[0] = Integer.parseInt(inputRowCol[0]);
            rowColumn[1] = Integer.parseInt(inputRowCol[1]);
            if ( !isNotOutOfBounds(rowColumn[0], rowColumn[1]) || !isInteger(inputRowCol[0]) || !isInteger(inputRowCol[1]) ){
                throw new Exception();
            }

        }catch (Exception exception){
            System.out.println("The Number you tried to enter is not correct. Try again putting a integer of value 0-9 in both fields.");
            checkAndAssignRowAndColumn();
        }
    }

    private void setIconAttackedRowAndColumn(int [] rowColumn){
        if (attackHit){
            currentPlayerEnemyBoatsAttacked()[rowColumn[0]][rowColumn[1]] = boatState[1];
            nextPlayerTable()[rowColumn[0]][rowColumn[1]] = boatState[1];
            return;
        }
        currentPlayerEnemyBoatsAttacked()[rowColumn[0]][rowColumn[1]] = failedIcon;
        nextPlayerTable()[rowColumn[0]][rowColumn[1]] = failedIcon;
    }

    private String currentPlayerToString(){
        if (turn % 2 == 0){
            return "Player1";
        }
        return "Player2";
    }

    private char[][] currentPlayerTable(){
        if (turn % 2 == 0){
            return boatsPlayer1;
        }
        return boatsPlayer2;
    }

    private char[][] currentPlayerEnemyBoatsAttacked(){
        if (turn % 2 == 0){
            return boatsAttackedPlayer1;
        }
        return boatsAttackedPlayer2;
    }

    private char[][] nextPlayerTable(){
        if (turn % 2 != 0){
            return boatsPlayer1;
        }
        return boatsPlayer2;
    }

    private boolean gameRunning (char [][] currentPlayerTable){
        for (char[] chars : currentPlayerTable) {
            for (int z = 0; z < currentPlayerTable[0].length; z++) {
                if (chars[z] == boatState[0]) {
                    return true;
                }
            }
        }
        return false;
    }

    private void generateBoats(char [][] game){
        int row;
        int column;
        int [] boatArray = new int[]{1,2,3,4};
        boolean isVertical;
        boolean boatPlaced;

        for (int i = 0; i < boatArray.length; i++){
            for (int z = 0; z < boatArray[i]; z++){

                boatPlaced = false;
                while(!boatPlaced){
                    row = random.nextInt(GAME_LENGTH);
                    column = random.nextInt(GAME_WIDTH);
                    isVertical = random.nextBoolean();
                    if (boatCanBePlaced(game,row,column,boatArray.length -i, isVertical)){
                        placeBoat(game,row,column,boatArray.length -i, isVertical);
                        boatPlaced = true;
                    }
                }

            }

        }

    }

    private boolean boatCanBePlaced (char[][] game, int row, int column, int length, boolean isVertical){
        int counter = 0;
        int rowModified = row;
        int colModified = column;

        for (int i = 0; i < length; i++){
            if (!boatsInArea(game, rowModified, colModified)) {
                if (isNotOutOfBounds(rowModified, colModified)) {
                    counter++;
                }
            }
            if (isVertical){
                rowModified ++;
            }
            else{
                colModified ++;
            }
        }
        return counter == length;
    }

    private void placeBoat (char[][] game, int row, int column, int length, boolean isVertical){

        int rowModified = row;
        int colModified = column;

        for (int i = 0; i < length; i++){
            game[rowModified][colModified] = boatState[0];
            if (isVertical){
                rowModified ++;
            }
            else{
                colModified ++;
            }
        }
    }

    private boolean boatsInArea (char[][] game, int row, int column){

        for (int i = -1; i <= 1; i++){
            for (int z = -1; z <= 1; z++){
                if (isNotOutOfBounds(row + i, column + z ) ){
                    if ( ( row + i == row ) && ( column + z == column ) ){
                        continue;
                    }
                    if ( ( game[row +i][column +z] == boatState[0] ) ){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isNotOutOfBounds (int row, int column){
        return ( row < GAME_LENGTH ) && ( row > -1 ) && ( column < GAME_LENGTH ) && ( column > -1 );
    }
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(Exception e) {
            return false;
        }// only got here if we didn't return false
        return true;
    }



}
