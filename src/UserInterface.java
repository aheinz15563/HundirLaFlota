import java.util.Scanner;

public class UserInterface {
    private Scanner scanner = new Scanner(System.in);
    public String [] getRowColumn (){
        String [] resultado = new String[2];

        System.out.println("What Row would you like to attack?");
        resultado[0] = scanner.next();
        System.out.println("What Column would you like to attack?");
        resultado[1] = scanner.next();

        return resultado;
    }
}
