import java.util.Random;

public class HundirLaFlota {

    private final int GAME_LENGTH = 10;
    private final int GAME_WIDTH = 10;
    private final int BOAT_MAX_LENGTH = 4;
    private char [][] boatsPlayer1;
    private char [][] boatsPlayer2;
    private Print print;
    private UserInterface userInterface;
    private int [] rowColumn;
    private int turn;
    private Random random;
    private char [] boatState;
    private char failedIcon;
    private boolean positionIsFree;
    private int [][] incrementType;

    public HundirLaFlota(){
        boatsPlayer1 = new char[GAME_LENGTH][GAME_WIDTH];
        boatsPlayer2 = new char[GAME_LENGTH][GAME_WIDTH];
        userInterface = new UserInterface();
        print = new Print(this);
        rowColumn = new int[2];
        turn = 0;
        random = new Random();
        boatState = new char[]{'O', 'X', 'H'};
        failedIcon = '.';
        incrementType = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
        generateBoats(boatsPlayer1);
        generateBoats(boatsPlayer2);
    }

    public void start (){
        while ( gameRunning(currentPlayerTable()) ){
            print.printGame(currentPlayerTable(), nextPlayerTable());
            print.printPlayerTurn(currentPlayerToString());
            checkAndAssignRowAndColumn();
            positionIsFree = positionIsFree(rowColumn[0],rowColumn[1]);
            setAttackedPositionState(rowColumn[0],rowColumn[1]);
            assignTurn(rowColumn[0], rowColumn[1]);
            checkAndAssignDrownedBoats(rowColumn[0], rowColumn[1]);
            if (!positionIsFree){
                print.printSpaces();
                System.out.println("That position was already used... Try Again");
            }
        }
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

    private void checkAndAssignRowAndColumn(){
        String [] inputRowCol;
        try{
            inputRowCol = userInterface.getRowColumn();
            rowColumn[0] = Integer.parseInt(inputRowCol[0]);
            rowColumn[1] = Integer.parseInt(inputRowCol[1]);
            if ( !isNotOutOfBounds(rowColumn[0], rowColumn[1]) || !isInteger(inputRowCol[0]) || !isInteger(inputRowCol[1]) ){
                throw new Exception();
            }
        }catch (Exception exception){
            print.printRowColumnError();
            checkAndAssignRowAndColumn();
        }
    }

    private boolean positionIsFree (int row, int col){
        return nextPlayerTable()[row][col] == 'O' || nextPlayerTable()[row][col] == 0;
    }

    private boolean boatHit(int row, int col){
        return ( nextPlayerTable()[row][col] == boatState[0] ) || ( nextPlayerTable()[row][col] == boatState[1] );
    }

    private boolean boatDrowned (int row, int col){

        int rowIncrement;
        int colIncrement;

        if (nextPlayerTable()[row][col] == boatState[1]){
            for (int i = 0; i < incrementType.length;i++){
                rowIncrement = row;
                colIncrement = col;
                while ( ( isNotOutOfBounds (rowIncrement,colIncrement) ) && ( nextPlayerTable()[rowIncrement][colIncrement] != failedIcon ) && ( nextPlayerTable()[rowIncrement][colIncrement] != '\u0000' ) ){
                    if (nextPlayerTable()[rowIncrement][colIncrement] == boatState[0]){
                        return false;
                    }
                    rowIncrement += incrementType[i][0];
                    colIncrement += incrementType[i][1];
                }
            }
            return true;
        }
        return false;
    }

    private void drownBoat (int row, int col){

        int rowIncrement;
        int colIncrement;

        for (int i = 0; i < incrementType.length;i++){
            rowIncrement = row;
            colIncrement = col;
            while ( ( isNotOutOfBounds (rowIncrement,colIncrement) ) && ( nextPlayerTable()[rowIncrement][colIncrement] != failedIcon ) && ( nextPlayerTable()[rowIncrement][colIncrement] != '\u0000' ) ){
                nextPlayerTable()[rowIncrement][colIncrement] = boatState[2];
                rowIncrement += incrementType[i][0];
                colIncrement += incrementType[i][1];
            }
        }
    }

    private void checkAndAssignDrownedBoats(int row,int col){
        if (boatDrowned(row,col)){
            drownBoat(row,col);
        }
    }

    private void setAttackedPositionState(int row, int col){

        if (positionIsFree){
            if (boatHit(row,col)){
                nextPlayerTable()[row][col] = boatState[1];
                return;
            }
            nextPlayerTable()[row][col] = failedIcon;
        }
    }

    private void assignTurn(int row, int col){
        if ( ( boatHit(row,col) ) || ( !positionIsFree ) ) {
            return;
        }
        turn++;
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

    private char[][] nextPlayerTable(){
        if (turn % 2 != 0){
            return boatsPlayer1;
        }
        return boatsPlayer2;
    }

    private boolean isNotOutOfBounds (int row, int column){
        return ( row < GAME_LENGTH ) && ( row > -1 ) && ( column < GAME_WIDTH ) && ( column > -1 );
    }
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(Exception e) {
            return false;
        }// only got here if we didn't return false
        return true;
    }

    //GETTERS

    public int getGAME_LENGTH() {
        return GAME_LENGTH;
    }

    public int getGAME_WIDTH() {
        return GAME_WIDTH;
    }

    public char[][] getBoatsPlayer1() {
        return boatsPlayer1;
    }

    public char[][] getBoatsPlayer2() {
        return boatsPlayer2;
    }

    public Print getPrint() {
        return print;
    }

    public UserInterface getUserInterface() {
        return userInterface;
    }

    public int[] getRowColumn() {
        return rowColumn;
    }

    public int getTurn() {
        return turn;
    }

    public Random getRandom() {
        return random;
    }

    public char[] getBoatState() {
        return boatState;
    }

    public char getFailedIcon() {
        return failedIcon;
    }

    //SETTERS


    public void setBoatsPlayer1(char[][] boatsPlayer1) {
        this.boatsPlayer1 = boatsPlayer1;
    }

    public void setBoatsPlayer2(char[][] boatsPlayer2) {
        this.boatsPlayer2 = boatsPlayer2;
    }

    public void setPrint(Print print) {
        this.print = print;
    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public void setRowColumn(int[] rowColumn) {
        this.rowColumn = rowColumn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public void setBoatState(char[] boatState) {
        this.boatState = boatState;
    }

    public void setFailedIcon(char failedIcon) {
        this.failedIcon = failedIcon;
    }
}
