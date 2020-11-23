import java.util.Scanner;

public class FredJSinSteinSaksPapir {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String maskinensValg = "";
        String brukersValg = "";
        String uavgjortTekst = "Datamaskin er %s. Du er %s. Uavgjort";
        String vinnerTekst = "Datamaskin er %s. Du er %s. Du vant!";
        String taperTekst = "Datamaskin er %s. Du er %s. Du tapte!";
        String saks = "saks";
        String stein = "stein";
        String papir = "papir";



        for (int i = 0; i < 1000; i++) {
            System.out.println("");
            int tilfeldigTall = (int) (Math.random() * 3);

//        System.out.println("Saks (0), Stein (1) eller Papir (2)? ");
//        int brukerInput = input.nextInt();

            int brukerInput = (int) (Math.random() * 3);

            if (brukerInput >= 3 || brukerInput < 0) {
                System.out.println("Ugyldig tall, kun 0, 1 eller 2 aksepteres.");
                return;
            } else  {
                switch (tilfeldigTall)  {
                    case 0:
                        maskinensValg = saks;
                        break;
                    case 1:
                        maskinensValg = stein;
                        break;
                    case 2:
                        maskinensValg = papir;
                }
                switch (brukerInput)  {
                    case 0:
                        brukersValg = saks;
                        break;
                    case 1:
                        brukersValg = stein;
                        break;
                    case 2:
                        brukersValg = papir;
                }

                if (brukerInput == tilfeldigTall)   {
                    System.out.printf(uavgjortTekst, maskinensValg, brukersValg);

                } else  {
                    switch (brukersValg)  {
                        case "saks":
                            if (maskinensValg == "papir")
                                System.out.printf(vinnerTekst, maskinensValg, brukersValg);
                            else if (maskinensValg == "stein")
                                System.out.printf(taperTekst, maskinensValg, brukersValg);
                            break;
                        case "stein":
                            if (maskinensValg == "saks")
                                System.out.printf(vinnerTekst, maskinensValg, brukersValg);
                            else if (maskinensValg == "papir")
                                System.out.printf(taperTekst, maskinensValg, brukersValg);
                            break;
                        case "papir":
                            if (maskinensValg == "stein")
                                System.out.printf(vinnerTekst, maskinensValg, brukersValg);
                            else if (maskinensValg == "saks")
                                System.out.printf(taperTekst, maskinensValg, brukersValg);
                    }
                }
            }
        }


    }
}