public class DisplayAllUnicodeSymbols {

    public static void main(String[] args) {
        final int UNICODEMAX = 1112064;
        int jSum = -1;
        for (int i = 0; i < UNICODEMAX/30; i++) {

            for (int j = 0; j < 30; j++) {
                jSum ++;
                char a = (char) jSum;
                System.out.print(a + "\t");
            }
            System.out.println();
        }
    }
}
