public class Color {

    private static final String ANSI_YELLOW = "\u001B[93m";
    private static final String ANSI_BLUE = "\u001B[96m";

    public String [] getColorCodes (HundirLaFlota hundirLaFlota){
        if ( hundirLaFlota.getTurn() % 2 == 0){
            return new String[]{ANSI_BLUE,ANSI_YELLOW};
        }
        return new String[]{ANSI_YELLOW,ANSI_BLUE};
    }

}
