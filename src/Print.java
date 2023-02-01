public class Print {
    public void printGame (char [][] table){
        for (int i = 0; i < table.length; i++){
            for (int z = 0; z < table[0].length; z++){
                System.out.print(table[i][z] + "|");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printPlayerTurn (String player){
        System.out.println("It is " + player + "'s turn");
    }
}
