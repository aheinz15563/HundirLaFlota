import java.util.InputMismatchException;
import java.util.Scanner;

public class Exceptiii {
    int number = 5;
    Scanner scanner = new Scanner(System.in);

    public void changeNumber (){
        System.out.println("What is the number you would like to assign?");
        try{
            String input = scanner.next();
            if (isInteger(input)){
                number = Integer.parseInt(input);
                return;
            }
            throw new Exception();
        }catch(Exception exception){
            changeNumber();
        }
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
