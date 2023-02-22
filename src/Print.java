public class Print {
    public void printGame (char [][] table, char [][] table2){
        for (int i = 0; i < table.length; i++){
            for (int z = 0; z < table[0].length; z++){
                System.out.print(table[i][z] + "|");
            }
            System.out.print("   ");
            for (int x = 0; x < table2[0].length; x++){
                System.out.print(table2[i][x] + "|");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printPlayerTurn (String player){
        System.out.println("It is " + player + "'s turn");
    }
}
