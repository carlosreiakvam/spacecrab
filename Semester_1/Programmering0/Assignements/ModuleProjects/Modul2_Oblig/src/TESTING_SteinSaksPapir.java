import java.util.Scanner;

public class TESTING_SteinSaksPapir {
    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            // Generer tilfeldig tall
//        int max = 2, min = 0;
            int tilfeldigTall = (int) (Math.random() * 3);
            int spillerTall = (int) (Math.random() * 3);

            // Konverter valg av nummer til våpen
            String[] vaapen = {"Saks", "Stein", "Papir"};
            String dataVaapen = vaapen[(int) tilfeldigTall];
            String spillerVaapen = vaapen[spillerTall];

            // Lager beskjeder til bruker
            String scoreBeskjed = "Datamaskinen er %s. Du er %s. %s";
            String tap = "Du tapte...";
            String uavgjort = "Uavgjort";
            String vinn = "Du har vunnet!";

            // Avgjørelse
            String avgjorelse =
                    spillerTall == tilfeldigTall ? uavgjort :
                            spillerTall == tilfeldigTall - 1 ? tap :
                                    spillerTall == tilfeldigTall + 2 ? tap : vinn;
            System.out.printf(scoreBeskjed + "\n", dataVaapen, spillerVaapen, avgjorelse);
        }


    }
}
