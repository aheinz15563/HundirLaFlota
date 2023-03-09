public class Table {

    private char [][] table;
    private final int GAME_LENGTH = 10;
    private final int GAME_WIDTH = 10;

    public Table (){
        table = new char[GAME_LENGTH][GAME_WIDTH];
    }

    public boolean isNotOutOfBounds(int row, int column){
        return ( row < GAME_LENGTH ) && ( row > -1 ) && ( column < GAME_WIDTH ) && ( column > -1 );
    }

    // GETTER
    public char[][] getTable() {
        return table;
    }

    public int getGAME_LENGTH() {
        return GAME_LENGTH;
    }

    public int getGAME_WIDTH() {
        return GAME_WIDTH;
    }

    // SETTER

    public void setTable(char[][] table) {
        this.table = table;
    }

    public void setPositions (int row, int col, char a){
        table [row][col] = a;
    }
    public char getPositions (int row, int col){
        return table [row][col];
    }
}
