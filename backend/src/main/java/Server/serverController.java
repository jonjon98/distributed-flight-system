package Server;

import java.util.Scanner;

public class serverController {

    public static String displayServicesPage(){
        String displayService = """
                Services Provided:\n
                1. Search for flight ID\n
                2. Check flight details\n
                3. Book a flight\n
                4. Set update for flight ID\n
                5. Retrieve flight booking information\n
                6. Cancel existing booking reservation\n
                7. Previous Page\n\n
                Service selected (Number only):  
                """;
        System.out.println(displayService);
        return displayService;
    }

    public static String displaySemanticsPage(){
        String displaySemantics = """
                Semantics Option:\n
                1. at-least-once\n
                2. at-most-once\n
                3. Exit\n\n
                Option selected (Number only):  
                """;
        System.out.println(displaySemantics);
        return displaySemantics;
    }

    public static String[] getFlightID(String sourcePlace, String destinationPlace){
        String[] flightInformation = new String[3];
        String flightId;
        flightId = "YYTESDDMMI";


        flightInformation[0] = flightId;
        flightInformation[1] = sourcePlace;
        flightInformation[2] = destinationPlace;
        return flightInformation;
    }

    public static String[] getFlightInfo(String flightId){
        String[] flightInformation = new String[3];
        // String flightId;
        flightId = "YYTESDDMMI";


        flightInformation[0] = flightId;
        // flightInformation[1] = sourcePlace;
        // flightInformation[2] = destinationPlace;
        return flightInformation;
    }




}
