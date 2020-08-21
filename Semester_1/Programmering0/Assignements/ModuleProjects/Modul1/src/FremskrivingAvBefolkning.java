public class FremskrivingAvBefolkning {
    public static void main(String[] args) {
        double secInYear = 60*60*24*365;
        double currentPopulation = 312032486;
        double childrenBorn = secInYear/7;
        double deaths = secInYear/13;
        double immigrants = secInYear/45;
        double populationIncYear = childrenBorn - deaths + immigrants;
        double populationIn5Years = currentPopulation + (populationIncYear*5);
        System.out.println("The population in 5 years is: "+ populationIn5Years);


    }
}
