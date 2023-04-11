package numbers;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        printIntroduction();
        String request;
        String[] splitRequest;
        while(true){
            System.out.print("Enter a request: ");
            request = input.nextLine();
            System.out.println();
            splitRequest = request.split(" ");
            int numberOfParams = splitRequest.length;

            if(numberOfParams == 1){
                if(verifyFirstParam(splitRequest[0])){
                    if(splitRequest[0].equals("0")){
                        System.out.println("Bye!");
                        break;
                    }
                    singleNumberFullRequest(Long.parseLong(splitRequest[0]));
                }

            }else if(numberOfParams == 2){
                if(verifyFirstParam(splitRequest[0]) && verifySecondParam(splitRequest[1])){
                    processMultiNumbers(Long.parseLong(splitRequest[0]), Long.parseLong(splitRequest[1]));
                    System.out.println();
                }

            }else if(numberOfParams >= 3){
                String[] searchArray = Arrays.copyOfRange(splitRequest, 2, splitRequest.length);
                if(verifyFirstParam(splitRequest[0]) && verifySecondParam(splitRequest[1]) &&
                verifySearchParams(searchArray) && !containsMutuallyExclusiveTerms(searchArray)){
                    processMultiNumbers(Long.parseLong(splitRequest[0]), Long.parseLong(splitRequest[1]), searchArray);
                    System.out.println();
                }
            }
        }
    }

    public static void singleNumberFullRequest(long rawLongValue){
        SpecialNumbers holder = new SpecialNumbers(rawLongValue);
        holder.printFullStatusChart();
    }

    public static void processMultiNumbers(long startNumber, long numsToCheck){
        for(long i = 0; i < numsToCheck; i++){
            SpecialNumbers holder = new SpecialNumbers(startNumber + i);
            holder.printShortStatusChart();
        }
    }

    public static void processMultiNumbers(long startNumber, long numsToCheck, String[] searchParams){
        //Used to run the program with two or more search parameters
        for(int i = 0; i < searchParams.length; i++){
            searchParams[i] = searchParams[i].toLowerCase();
        }
        long counter = 0;
        long successfulNums = 0;
        while(successfulNums < numsToCheck){
            boolean passesTest = true;
            SpecialNumbers newNum = new SpecialNumbers(startNumber + counter++);
            for(String searchTerm : searchParams){
                boolean includeTerm = !searchTerm.contains("-");
                searchTerm = searchTerm.replace("-", "");
                boolean hasTerm = newNum.getShortStatus().contains(searchTerm);
                if((!hasTerm && includeTerm) || (hasTerm && !includeTerm)){
                    passesTest = false;
                    break;
                }
            }
            if(passesTest){
                System.out.println(newNum.getShortStatus());
                successfulNums++;
            }else{
                continue;
            }
        }
    }


    public static void printIntroduction(){
        System.out.println("Welcome to Amazing Numbers!");
        System.out.println();
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println(" * the first parameter represents a starting number;");
        System.out.println(" * the second parameter shows how many consecutive numbers are to be printed;");
        System.out.println("- two natural numbers and properties to search for;");
        System.out.println("- a property preceded by minus must not be present in numbers;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.");
        System.out.println();
    }

    public static boolean verifyFirstParam(String argument) {
        long firstParamAsLong;
        try{
            firstParamAsLong = Long.parseLong(argument);
            if(firstParamAsLong < 0){
                System.out.println("The first parameter should be a natural number or zero.\n");
                return false;
            }
        }catch(NumberFormatException e){
            System.out.println("The first parameter should be a natural number or zero.\n");
            return false;
        }
        return true;
    }

    public static boolean verifySecondParam(String argument) {
        long secondParamAsLong;
        try{
            secondParamAsLong = Long.parseLong(argument);
            if(secondParamAsLong < 0){
                System.out.println("The second parameter should be a natural number.\n");
                return false;
            }
        }catch(NumberFormatException e){
            System.out.println("The second parameter should be a natural number.\n");
            return false;
        }
        return true;
    }

    public static boolean verifySearchParams(String[] arguments){
        StringBuilder failedProperties = new StringBuilder();
        var propertyList = Arrays.asList(SpecialNumbers.PROPERTY_LIST.split(", "));
        for(String argument : arguments){
            if(!propertyList.contains(argument.toUpperCase().replace("-", ""))){
                failedProperties.append(String.format(" %s", argument.toUpperCase()));
            }
        }
        String[] failedArray = failedProperties.toString().strip().split(" ");
        if(failedArray[0].equals("")){
            //System.out.println("No failures noted!");
            return true;
        }else if(failedArray.length == 1){
            System.out.printf("The property [%s] is wrong.\n", failedArray[0].toUpperCase());
            System.out.printf("Available properties: [%s]\n", SpecialNumbers.PROPERTY_LIST);
            System.out.println();
            return false;
        }else{
            System.out.println("Multi-property fail");
            String failureMessage = "The properties [" + failedArray[0].toUpperCase();
            for(int i = 1; i < failedArray.length; i++){
                failureMessage += ", " + failedArray[i].toUpperCase();
            }
            failureMessage += "] are wrong.";
            System.out.println(failureMessage);
            System.out.printf("Available properties: [%s]\n", SpecialNumbers.PROPERTY_LIST);
            System.out.println();
            return false;
        }
    }

    public static boolean containsMutuallyExclusiveTerms(String[] searchArray){
        for(int i = 0; i < searchArray.length; i++){
            searchArray[i] = searchArray[i].toUpperCase();
        }
        var searchList = Arrays.asList(searchArray);
        if(searchList.contains("DUCK") && searchList.contains("SPY")){
            System.out.println("The request contains mutually exclusive properties: [DUCK, SPY]");
            System.out.println("There are no numbers with these properties");
            System.out.println();
            return true;
        }else if(searchList.contains("EVEN") && searchList.contains("ODD")){
            System.out.println("The request contains mutually exclusive properties: [EVEN, ODD]");
            System.out.println("There are no numbers with these properties");
            System.out.println();
            return true;
        }else if(searchList.contains("SQUARE") && searchList.contains("SUNNY")){
            System.out.println("The request contains mutually exclusive properties: [SQUARE, SUNNY]");
            System.out.println("There are no numbers with these properties");
            System.out.println();
            return true;
        }else if(searchList.contains("HAPPY") && searchList.contains("SAD")){
            System.out.println("The request contains mutually exclusive properties: [HAPPY, SAD]");
            System.out.println("There are no numbers with these properties");
            System.out.println();
            return true;
        }else if(searchList.contains("-ODD") && searchList.contains("-EVEN")){
            System.out.println("The request contains mutually exclusive properties: [-ODD, -EVEN]");
            System.out.println("There are no numbers with these properties");
            System.out.println();
            return true;
        }else if(searchList.contains("-HAPPY") && searchList.contains("-SAD")){
            System.out.println("The request contains mutually exclusive properties: [-HAPPY, -SAD]");
            System.out.println("There are no numbers with these properties");
            System.out.println();
            return true;
        }else{
            String[] properties = SpecialNumbers.PROPERTY_LIST.split(", ");
            for(String property : properties){
                if(searchList.contains(property) && searchList.contains("-" + property)){
                    System.out.printf("The request contains mutually exclusive properties: [%s, -%s]\n", property, property);
                    System.out.println("There are no numbers with these properties");
                    System.out.println();
                    return true;
                }
            }
            return false;
        }
    }
}
