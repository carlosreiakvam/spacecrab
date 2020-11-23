import java.util.Scanner;

public class TESTING_Input {
    public static void main(String[] args) {

        System.out.print("Oppgi et tall mellom 0 og 999: ");
        Scanner input = new Scanner(System.in);
        double tall = input.nextDouble();
        double sjekk = tall % 10;
        System.out.println(sjekk);
    }
}
