import java.util.Scanner;

public class AvstandMellomToPunkterPaaJorda {
    public static void main(String[] args) {

        // Få input fra bruker
        Scanner input = new Scanner(System.in);
        System.out.println("Oppgi breddegrad og lengdegrad for punkt 1: ");
        double x1 = input.nextDouble();
        double y1 = input.nextDouble();

        System.out.println("Oppgi breddegrad og lengdegrad for punkt 1: ");
        double x2 = input.nextDouble();
        double y2 = input.nextDouble();

        //konverterer input fra radius til grader
        double x1Rad = Math.toRadians(x1);
        double x2Rad = Math.toRadians(x2);
        double y1Rad = Math.toRadians(y1);
        double y2Rad = Math.toRadians(y2);

        // Beregn avstand mellom de to punktene
        final double RADIUS = 6371.01;
        double avstand = RADIUS * Math.acos(Math.sin(x1Rad) * Math.sin(x2Rad)
                + Math.cos(x1Rad) * Math.cos(x2Rad) * Math.cos(y1Rad - y2Rad));

        // Vis svar til bruker
        System.out.printf("Avstanden mellom punktene (%.3f,%.3f) og (%.3f,%.3f) er %.3f km.", x1, y1, x2, y2, avstand);
    }
}
