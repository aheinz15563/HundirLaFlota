import java.util.Random;

public class HundirLaFlota {

    private final int GAME_LENGTH = 10;
    private final int GAME_WIDTH = 10;
    private char [][] tablePlayer1;
    private char [][] tablePlayer2;
    private Print print;
    private UserInterface userInterface;
    private int [] rowColumn;
    private int turn;
    private final int numberOfBoats = 10;
    private final int BOAT_MAX_LENGTH = 4;
    private Random random;
    private char [] boatState;

    public HundirLaFlota(){
        tablePlayer1 = new char[GAME_LENGTH][GAME_WIDTH];
        tablePlayer2 = new char[GAME_LENGTH][GAME_WIDTH];
        userInterface = new UserInterface();
        print = new Print();
        rowColumn = new int[2];
        turn = 0;
        random = new Random();
        boatState = new char[]{'O', 'X'};

        tablePlayer1 = generateBoats(tablePlayer1);
    }

    public void start (){
        while ( gameRunning() ){
            print.printGame(tablePlayer1);
            print.printPlayerTurn(currentPlayer());
            rowColumn = userInterface.getRowColumn();
            turn++;
        }
    }

    public String currentPlayer(){
        if (turn % 2 == 0){
            return "Player1";
        }
        return "Player2";
    }

    private boolean gameRunning (){
        return true;
    }

    private char [][] generateBoats(char [][] game){
        int row;
        int column;
        int counter = 0;
        boolean isVertical = true;
        int lengthCounter = 0;
        int timesThatABoatOfSameLengthGetsCreated = 4;

        while( timesThatABoatOfSameLengthGetsCreated < ( (BOAT_MAX_LENGTH +1) - lengthCounter ) ){

        }
        lengthCounter++;

        while (counter <= numberOfBoats){
            row = random.nextInt(GAME_LENGTH);
            column = random.nextInt(GAME_WIDTH);
            if (!boatsInArea(game,row,column)){
                if (boatCanBePlaced(game,row,column,BOAT_MAX_LENGTH - lengthCounter, isVertical)){
                    game[row][column] = boatState[0];
                    counter++;
                }
            }
        }
        return game;
    }

    private boolean boatCanBePlaced (char[][] game, int row, int column, int length, boolean isVertical){
        for (int i = 0; i <= length; i++){
            if (!boatsInArea(game,row,column)){
                if (isVertical && isNotOutOfBounds(row +i, column)){
                    game[row + i][column] = boatState[0];
                    continue;
                }
                if (isNotOutOfBounds(row, column +i)){
                    game[row][column +i] = boatState[0];
                    continue;
                }
            }
            return false;
        }
        return true;
    }

    private boolean boatsInArea (char[][] game, int row, int column){

        for (int i = -1; i <= 1; i++){
            for (int z = -1; z <= 1; z++){
                if (isNotOutOfBounds(row + i, column + z ) ){
                    if ( game[row +i][column +z] == boatState[0] ){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // SE ESTA SALTANDO LOS ADJACIENTES VERTICALES Y HORIZONTALES.
    private boolean isNotOutOfBounds (int row, int column){
        return ( row < GAME_LENGTH ) && ( row > -1 ) && ( column < GAME_LENGTH ) && ( column > -1 );
    }

}
