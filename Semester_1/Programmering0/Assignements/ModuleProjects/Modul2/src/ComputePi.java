public class BeregnPi {
    public static void main(String[] args) {

        double sequence = 1;
        double pi = 0;

        for (int i = 1; i < 20000; i++) {
            double sign = (Math.pow(-1, i));
            sequence += sign / (1 + (2 * i));
            pi = 4 * sequence;
            System.out.println("pi with " + i + " terms :" + pi);
        }

        final double PI = pi;
    }
}