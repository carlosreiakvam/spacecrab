public class TesterGenereltModul3 {
    public static void main(String[] args) {
        System.out.println(Math.floor(3.6));

        String s = "Java";
        System.out.println(s.startsWith("A"));

        System.out.println(s.indexOf("J"));
        System.out.println(s.substring(0,4).equals("Java"));
        System.out.println(s.charAt(0) == 'J' && s.charAt(1) == 'a' && s.charAt(2) == 'v' && s.charAt(3) == 'a');
        System.out.println("SELECT".substring(0,5));

        System.out.println(Math.rint(3.6));

        int i = (int)Math.sin(Math.PI);
        System.out.println(i);

        int j = (int) Math.sin(Math.toRadians(90));
        int k = (int) Math.toDegrees(Math.sin(90));

        System.out.println(j);
        System.out.println(k);

        char x = Integer.toHexString(3).charAt(0);
        System.out.println(Integer.toHexString(15)+"ape");
        System.out.println(2+2);



        char ape = Integer.toHexString(15).charAt(0);
        char charo = 'p';
        char concatTest = (char) (45 + 45);
        System.out.println(concatTest);


    }
}
