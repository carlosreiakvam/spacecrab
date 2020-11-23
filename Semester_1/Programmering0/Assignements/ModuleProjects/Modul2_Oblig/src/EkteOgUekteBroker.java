import java.util.Scanner;

public class EkteOgUekteBroker {
    public static void main(String[] args) {

        // Få teller og nevner fra bruker
        System.out.print("Oppgi teller: ");
        Scanner input = new Scanner(System.in);
        int teller = input.nextInt();
        System.out.print("Oppgi nevner: ");
        int nevner = input.nextInt();

        // Inspiser brøker og gi beskjed til bruker
        if (teller / nevner < 1) {
            System.out.printf("%d / %d er en ekte brøk.", teller, nevner);
        } else if (teller % nevner == 0) {
            System.out.printf("%d / %d er en uekte brøk.\nDen kan reduseres til %d", teller, nevner, teller / nevner);
        } else {
            int addend = teller / nevner; // addend til rest av brøk
            double redusertTeller = (teller % nevner); // tellerdel i rest av brøk
            System.out.printf("%d / %d er en uekte brøk.\n" +
                    "Det er et blandet tall %d + (%.0f / %d)", teller, nevner, addend, redusertTeller, nevner);
        }
    }
}