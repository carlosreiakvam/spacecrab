import java.util.Scanner;

public class SteinSaksPapir {
    public static void main(String[] args) {

        // Generer tilfeldig tall
        int dataTall = (int) (Math.random() * 3);

        // Bruker-input med enkel error-sjekk
        System.out.println("Saks (0), Stein (1) eller Papir (2)?");
        Scanner input = new Scanner(System.in);
        int spillerTall = input.hasNextInt() ? input.nextInt() : 999;
        boolean run = spillerTall >= 0 && spillerTall <= 2;

        // Start spillet hvis gyldig input, eller vis error-beskjed
        if (run) {

            // Konverter valg av nummer til våpen
            String spillerVaapen = spillerTall == 0 ? "Saks" :
                    spillerTall == 1 ? "Stein" : "Papir";

            String dataVaapen = dataTall == 0 ? "Saks" :
                    dataTall == 1 ? "Stein" : "Papir";

            // Lag beskjeder til bruker
            String scoreBeskjed = "Datamaskinen er %s. Du er %s. %s";
            String tap = "Du tapte...";
            String uavgjort = "Uavgjort";
            String vinn = "Du har vunnet!";

            // Beregn avgjørelse
            String avgjorelse =
                    spillerTall == dataTall ? uavgjort :
                            spillerTall == dataTall - 1 ? tap :
                                    spillerTall == dataTall + 2 ? tap : vinn;

            // Print avgjørelse til bruker
            System.out.printf(scoreBeskjed, dataVaapen, spillerVaapen, avgjorelse);

        } else {
            System.out.println("Ugyldig tall, kun 0, 1 eller 2 aksepteres.");
        }
    }
}
