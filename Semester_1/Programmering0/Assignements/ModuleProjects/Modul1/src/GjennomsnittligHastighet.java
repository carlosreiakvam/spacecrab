public class GjennomsnittligHastighet {
    public static void main(String[] args) {
        double timeInSecs = 60*60+40*60+35;
        double secondsPerMIle = timeInSecs/24;
        double mph = 3600/secondsPerMIle;
        double kph = mph*1.6;
        System.out.println(kph);
    }
}
