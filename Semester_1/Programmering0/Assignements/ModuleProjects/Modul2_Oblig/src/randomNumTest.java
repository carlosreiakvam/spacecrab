public class randomNumTest {
    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            double n = Math.round(Math.random()*2);
            System.out.println(n);
        }

    }
}
