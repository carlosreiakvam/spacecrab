import java.util.Scanner;

public class SummerSiffer {
    public static void main(String[] args) {

        // Få input fra bruker
        System.out.print("Oppgi et tall mellom 0 og 999: ");
        Scanner input = new Scanner(System.in);
        int inputNumber = input.nextInt();

        // Gjør utregninger og vis til bruker, eller vis errorbeskjed
        if (inputNumber <= 999 && inputNumber >= 0) {
            int digit1 = inputNumber % 10;
            int digit2 = (inputNumber / 10) % 10;
            int digit3 = (inputNumber / 100) % 10;
            int sum = digit1 + digit2 + digit3;

            System.out.printf("Summen av siffer i %d er: %d\n\n", inputNumber, sum);
        } else System.out.println("Tallet må være mellom 0 og 999!\n");
    }
}