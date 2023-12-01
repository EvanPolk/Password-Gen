import java.util.HashSet;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        new GUI();
        testGroups(true, true, true, true, 12);
    }

    /* My backend function handles the logic of actually generating a password, which I wished to separate
     * from the GPU class.
     * Essentially has selectFactor (random num 0-3) to find a setting that is set to true, if it isn't it
     * generates another selectFactor and tries again. Then it calls a getObject function which takes a
     * random object in a list (a symbol, or a num, or chars A-Z etc.)
     * Pre-condition : four boolean values representing checkboxes, length is a number between 1-100 ensured by spinner
     * Post-condition: returns a string with random chars from four groups length 1-100 depending on length given
     */
    public static String backend(boolean upperCase, boolean lowerCase, boolean numbers,
                               boolean symbols, int length) {
        Random r = new Random();
        String password = "";
        boolean[] settings = {false, upperCase, lowerCase, numbers, symbols};
        // creating a loop the length of password trying to generate random chars (Technically strings)
        for (int i = 1; i <= length; i++) {
            // A precondition validation to make sure the getObject function called is something the user
            // wanted ie if upperCase was left unchecked we don't want to call that function
            int selectFactor = 0;
            while (!settings[selectFactor]) {
                selectFactor = r.nextInt(4) + 1;
            }

            // final else if tree for getObject functions
            if (selectFactor == 1) {
                password += getUpperCase(r);
            } else if (selectFactor == 2) {
                password += getLowerCase(r);
            } else if (selectFactor == 3) {
                password += getNumber(r);
            } else if (selectFactor == 4) {
                password += getSymbol(r);
            }
        }
        return password;
    }

    // returns random upperCase
    public static String getUpperCase(Random r) {
        String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
                            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        return alphabet[r.nextInt(26)];
    }

    // returns random lowerCase
    public static String getLowerCase(Random r) {
        String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
                            "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        return alphabet[r.nextInt(26)];
    }

    // returns random int
    public static int getNumber(Random r) {
        return r.nextInt(10);
    }

    // returns random symbol
    public static String getSymbol(Random r) {
        String[] symbols = {"!", "@", "#", "$", "%", "&", "*"};
        return symbols[r.nextInt(6)];
    }
    // Find a way to consistently test all groups are here via regex TODO!!!!
    // This has a bug that I haven't fixed yet
    public static void testGroups(boolean upperCase, boolean lowerCase, boolean numbers, boolean symbols, int length) {
        int amntCorrect = 0;
        for(int i = 1; i <= 50; i++) {
            String password = backend(upperCase, lowerCase, numbers, symbols, length);
            boolean upperCaseFlag = false;
            boolean lowerCaseFlag = false;
            boolean numbersFlag = false;
            boolean symbolsFlag = false;
            for(int j = 0; j < password.length() - 1; j++) {
                char temp = password.charAt(j);
                if(Character.isUpperCase(temp))
                    upperCaseFlag = true;
                if(Character.isLowerCase(temp))
                    lowerCaseFlag = true;
                if(Character.isDigit(temp))
                    numbersFlag = true;
                if(temp == '!' || temp == '@' || temp == '#' || temp == '$' || temp == '%' || temp == '*')
                    symbolsFlag = true;
            }
            if(upperCase == upperCaseFlag && lowerCase == lowerCaseFlag &&
                    numbers == numbersFlag && symbols == symbolsFlag)
                amntCorrect++;
        }
        System.out.println("Out of 50 tested there were " + amntCorrect + " with all groups contained in the password");
    }
}