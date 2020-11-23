import java.util.Locale;
import java.util.Scanner;

public class BMI {
    public static void main(String[] args) {

        // Få input fra bruker
        System.out.print("Oppgi din vekt i kg: ");
        Scanner input = new Scanner(System.in).useLocale(Locale.US);
        double vekt = input.nextDouble();

        System.out.print("Oppgi din høyde i meter: ");
        double hoyde = input.nextDouble();

        // Regn BMI
        double bmi = vekt / Math.pow(hoyde, 2);

        // Vis BMI til bruker
        System.out.printf("Din BMI er: %.2f kg/m^2", bmi);
    }
}