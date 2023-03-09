public class Boat {

    private boolean sunk;
    public Boat(){
        sunk = false;
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
}
