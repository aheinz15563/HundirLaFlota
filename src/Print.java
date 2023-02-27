public class Print {

    private HundirLaFlota hundirLaFlota;
    private static final String ANSI_YELLOW ="\u001B[93m";
    private static final String ANSI_BLUE ="\u001B[96m";

    public Print (HundirLaFlota hundirLaFlota){
        this.hundirLaFlota = hundirLaFlota;
    }

    public void printGame (char [][] table, char [][] table2){
        String [] colorCodes = getColorCodes();

        for (int i = 0; i < table.length; i++){
            System.out.print(i);
            for (int z = 0; z < table[0].length; z++){
                System.out.print(colorCodes[0] + table[i][z] + "|");
            }
            System.out.print("   ");
            for (int x = 0; x < table2[0].length; x++){
                if (table2[i][x] == hundirLaFlota.getBoatState()[1] || table2[i][x] == hundirLaFlota.getFailedIcon() || table2[i][x] == hundirLaFlota.getBoatState()[2]){
                    System.out.print( colorCodes[1] + table2[i][x] + "|");
                    continue;
                }
                System.out.print(colorCodes[1] + 0 + "|");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printPlayerTurn (String player){
        String [] colorCodes = getColorCodes();

        System.out.println(colorCodes[0] + "It is " + player + "'s turn");
    }

    public void printSpaces (){
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public void printRowColumnError (){
        System.out.println("The Number you tried to enter is not correct. Try again putting a integer of value 0-9 in both fields.");
    }

    public String [] getColorCodes (){
        if (hundirLaFlota.getTurn() % 2 == 0){
            return new String[]{ANSI_BLUE,ANSI_YELLOW};
        }
        return new String[]{ANSI_YELLOW,ANSI_BLUE};
    }


    // GETTER
    public HundirLaFlota getHundirLaFlota() {
        return hundirLaFlota;
    }
    // SETTER
    public void setHundirLaFlota(HundirLaFlota hundirLaFlota) {
        this.hundirLaFlota = hundirLaFlota;
    }
}
