import java.util.Scanner;

public class UserInterface {
    private Scanner scanner = new Scanner(System.in);
    public int [] getRowColumn (){
        int [] resultado = new int[2];

        System.out.println("What Row would you like to attack?");
        resultado[0] = scanner.nextInt();
        System.out.println("What Column would you like to attack?");
        resultado[1] = scanner.nextInt();

        return resultado;
    }
}
