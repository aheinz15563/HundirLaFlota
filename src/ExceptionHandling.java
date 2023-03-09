public class ExceptionHandling {
    public ExceptionHandling(){}

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(Exception e) {
            return false;
        }// only got here if we didn't return false
        return true;
    }
}
