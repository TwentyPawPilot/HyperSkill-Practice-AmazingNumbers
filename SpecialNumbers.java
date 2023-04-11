package numbers;

import java.util.Arrays;
import java.util.List;

class SpecialNumbers {
    public static final String PROPERTY_LIST =
            "BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SUNNY, SQUARE, JUMPING, HAPPY, SAD";
    private boolean buzz, duck, palindromic, gapful, spy, even, odd;
    private boolean square, sunny, jumping, happy, sad;
    private long value;
    private String valueAsString;
    private String shortStatus;

    public String getShortStatus() {
        return shortStatus;
    }

    public SpecialNumbers(long value) {
        this.value = value;
        String parity = value % 2 == 0 ? "even" : "odd";
        this.even = parity.equals("even");
        this.odd = parity.equals("odd");
        this.shortStatus = String.format("%,d is %s", this.value, parity);
        this.valueAsString = String.valueOf(this.value);
        checkBuzzStatus(false);
        checkDuckStatus(false);
        checkPalindromeStatus(false);
        checkGapfulStatus(false);
        checkSpyStatus(false);
        checkSunnyAndSquareStatus(false);
        checkJumpingStatus();
        checkHappySadStatus();
    }

    protected void checkBuzzStatus(boolean printout){
        boolean isDivisibleBySeven = this.value % 7 == 0;
        boolean endsInSeven = this.value % 10 == 7;
        //System.out.println("This number is " + this.parity);
        if(isDivisibleBySeven || endsInSeven){
            //System.out.println("It is a Buzz number.");
            this.buzz = true;
            this.shortStatus += ", buzz";
        }else{
            //System.out.println("It is not a Buzz number");
            this.buzz = false;
        }
        if(printout) {
            System.out.println("Explanation:");
            if (isDivisibleBySeven && endsInSeven) {
                System.out.printf("%d is divisible by 7 and ends with 7.\n", this.value);
            } else if (isDivisibleBySeven) {
                System.out.printf("%d is divisible by 7.\n", this.value);
            } else if (endsInSeven) {
                System.out.printf("%d ends with 7.\n", this.value);
            } else {
                System.out.printf("%d is neither divisible by 7 nor does it end with 7.\n", this.value);
            }
        }
    }

    protected void checkDuckStatus(boolean printout){
        long valueHolder = this.value;
        this.duck = false;
        while(valueHolder > 0){
            if(valueHolder % 10 == 0){
                this.duck = true;
                this.shortStatus += ", duck";
                break;
            }else{
                valueHolder = valueHolder / 10;
            }
        }
        if(printout){
            System.out.printf("Duck status of %d is %b\n", this.value, this.duck);
        }
    }

    protected void checkPalindromeStatus(boolean printout){
        String backwards = Long.toString(this.value);
        this.palindromic = true;
        for(int i = 0; i < backwards.length() / 2; i++){
            if(backwards.charAt(i) != backwards.charAt(backwards.length() -(i+1))){
                this.palindromic = false;
                break;
            }
        }
        if(this.palindromic){
            this.shortStatus += ", palindromic";
        }
        if(printout){
            System.out.printf("The palindrome status of %d is %b\n", this.value, this.palindromic);
        }
    }

    private void checkGapfulStatus(boolean printout){
        if(this.value < 100){
            this.gapful = false;
        }else {
            String valueAsString = String.valueOf(this.value);
            StringBuilder divisor = new StringBuilder();
            divisor.append(valueAsString.charAt(0));
            divisor.append(valueAsString.charAt(valueAsString.length() - 1));
            this.gapful = this.value % Integer.parseInt(divisor.toString()) == 0;
            if (this.gapful) {
                this.shortStatus += ", gapful";
            }
        }
        if(printout){
            System.out.printf("The gapful status of %d is %b", this.value, this.gapful);
        }
    }

    private void checkSpyStatus(boolean printout){
        long digitHolder = this.value;
        long product = 1;
        long sum = 0;
        while(digitHolder > 0){
            product *= digitHolder % 10;
            sum += digitHolder % 10;
            digitHolder /= 10;
        }
        if(product == sum){
            this.spy = true;
            this.shortStatus += ", spy";
        }
        else{
            this.spy = false;
        }
        if(printout){
            System.out.printf("The spy status of %d id %b\n", this.value, this.spy);
        }
    }

    protected void checkSunnyAndSquareStatus(boolean printout){
        if(Math.sqrt((double)this.value) % 1 == 0){
            this.square = true;
            this.shortStatus += ", square";
        }else if(Math.sqrt((double)(this.value + 1)) % 1 == 0){
            this.sunny = true;
            this.shortStatus += ", sunny";
        }else{
            this.square = false;
            this.sunny = false;
        }
        if(printout){
            System.out.printf("The square and sunny status of %d is %b and %b respectively\n",
                    this.value, this.square, this.sunny);
        }
    }

    protected void checkJumpingStatus(){
        char[] individualDigits = String.valueOf(this.value).toCharArray();
        for(int i = 0; i < individualDigits.length - 1; i++){
            if(Math.abs(Character.getNumericValue(individualDigits[i]) - Character.getNumericValue(individualDigits[i + 1])) != 1){
                this.jumping = false;
                return;
            }
        }
        this.jumping = true;
        this.shortStatus += ", jumping";
    }

    protected void checkHappySadStatus(){
        long checkedValue = this.value;
        do{
            String valueAsString = String.format("%d", checkedValue);
            String[] digits = valueAsString.split("");
            int summation = 0;
            for(String digit : digits){
                summation += (Integer.parseInt(digit) * Integer.parseInt(digit));
            }
            checkedValue = summation;
        }while(checkedValue > 9);
        if(checkedValue == 0 || checkedValue == 1 || checkedValue == 7){
            this.happy = true;
            this.sad = false;
            this.shortStatus += ", happy";
        }else{
            this.happy = false;
            this.sad = true;
            this.shortStatus += ", sad";
        }
    }

    protected void printFullStatusChart(){
        System.out.printf("""
                        Properties of %,d
                        \teven: %b
                        \todd: %b
                        \tbuzz: %b
                        \tduck: %b
                        \tpalindromic: %b
                        \tGapful: %b
                        \tSpy: %b
                        \tsunny: %b
                        \tsquare: %b
                        \tjumping: %b
                        \thappy: %b
                        \tsad: %b
                        
                        """,
                this.value, this.even, this.odd, this.buzz, this.duck,
                this.palindromic, this.gapful, this.spy,
                this.sunny, this.square, this.jumping, this.happy, this.sad);
    }

    protected void printShortStatusChart(){
        System.out.println(this.shortStatus);
    }
}
