import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CLICalculator {
    private static HashMap<Character, Integer> opMap;
    public static void main(String[] args) {
        opMap = new HashMap<>();
        opMap.put('+', 1); opMap.put('-', 1); opMap.put('*', 2);
        opMap.put('/', 2); opMap.put('^', 3);

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to this text-based calculator.");
        System.out.println();
        while (true) {
            System.out.println("Enter an expression (press ENTER to quit).");
            System.out.println("To get the list of commands type !help.");
            String arithmeticString = getExpression(input);
            if (arithmeticString.length() == 0) break;
            System.out.println();
            if (arithmeticString.charAt(0) == '!') {
                try {
                    printCommandResult(arithmeticString);
                } catch (Exception error) {
                    System.out.println("Invalid command.");
                }

            }
            else {
                try {
                    System.out.println(evaluate(arithmeticString));
                } catch (Exception error) {
                    System.out.println("Syntax error.");
                }
            }
            System.out.println();
        }
    }

    // Collects input line and returns formatted string
    public static String getExpression(Scanner input) {
        String inputLine = input.nextLine();
        String arithmeticString = "";
        for (int i = 0; i < inputLine.length(); i++)
            if (inputLine.charAt(i) != ' ')
                arithmeticString += inputLine.charAt(i);
        return arithmeticString;
    }

    public static void printCommandResult(String str) {
        switch(str) {
            case "!help":
                System.out.println("The available commands are:");
                System.out.println("!ops - Displays operations and their uses.");
                System.out.println("!funcs - Displays functions and their uses.");
                System.out.println("!clear - Clears the terminal window.");
                break;
            case "!ops":
                System.out.println("The operators are:");
                System.out.println("The + operator adds two numbers - precedence of 1.");
                System.out.println("The - operator subtracts two numbers - precedence of 1.");
                System.out.println("The * operator multiplies two numbers - precedence of 2.");
                System.out.println("The / operator divides two numbers - precedence of 2.");
                System.out.println("The ^ operator adds two numbers - precedence of 3.");
                System.out.println("Note: a higher precedence value is evaluated first.");
                break;
            case "!funcs":
                System.out.println("The functions are:");
                System.out.println("The sqrt() function returns the square root of an expression.");
                System.out.println("The log_10() function returns the log base 10 of an expression.");
                System.out.println("The logn() function returns the natural log of an expression.");
                System.out.println("The sin() function return the sine of an expression in radians.");
                System.out.println("The cos() function returns the cosine of an expression in radians.");
                System.out.println("The tan() function returns the tangent of an expression in radians.");
                System.out.println("Note: functions are always evaluated before operators.");
                break;
            case "!clear":
                for (int i = 0; i < 50; i++)
                    System.out.println();
                break;
            default:
                throwError();
        }
    }

    // Evaluates an arithmetic string
    public static double evaluate(String str) {
        ArrayList<Double> nums = new ArrayList<>();
        ArrayList<Character> ops = new ArrayList<>();
        separateString(str, nums, ops);
        int level = 3;
        while (level > 0) {
            for (int i = 0; i < ops.size(); i++) {
               char op = ops.get(i);
               if (opMap.get(op) == level) {
                   nums.set(i, opResult(op, nums.get(i), nums.get(i + 1)));
                   nums.remove(i + 1);
                   ops.remove(i--);
               }
            }
            level--;
        }
        return nums.get(0);
    }

    // Split separate string values and operations into arraylists
    private static void separateString(String str, ArrayList<Double> nums, ArrayList<Character> ops) {
        String currentValue = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (opMap.containsKey(c)) {
                if (c == '-' && (i == 0 || opMap.containsKey(str.charAt(i - 1))))
                    currentValue += c;
                else if (i == str.length() - 1)
                    throwError();
                else {
                    ops.add(c);
                    if (currentValue.length() == 0) throwError();
                    else nums.add(Double.parseDouble(currentValue));
                    currentValue = "";
                }
            }
            else if (c == '(') {
                int rightParenCount = 1;
                for (int j = i + 1; j < str.length(); j++) {
                    char c2 = str.charAt(j);
                    if (c2 == '(') rightParenCount++;
                    else if (c2 == ')') {
                        rightParenCount--;
                        if (rightParenCount == 0) {
                            double value = evaluate(str.substring(i + 1, j));
                            int negative = 1;
                            if (currentValue.length() == 0)
                                currentValue = "" + value;
                            else {
                                if (currentValue.charAt(0) == '-') {
                                    currentValue = currentValue.substring(1);
                                    negative = -1;
                                }
                                if (currentValue.length() == 0)
                                    currentValue = "" + value * negative;
                                else
                                    currentValue = "" + functionResult(currentValue, value) * negative;
                            }
                            i = j;
                            break;
                        }
                    }
                }
                if (rightParenCount > 0) throwError();
            }
            else currentValue += c;
        }
        if (currentValue.length() == 0) throwError();
        else nums.add(Double.parseDouble(currentValue));
    }

    // Determines the value of an operation
    private static double opResult(char op, double num1, double num2) {
        return switch(op) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> num1 / num2;
            case '^' -> Math.pow(num1, num2);
            default -> throw new IllegalStateException("Error");
        };
    }

    // Determines the value of a function
    private static double functionResult(String function, double num) {
        return switch(function) {
            case "sqrt" -> Math.sqrt(num);
            case "log_10" -> Math.log10(num);
            case "logn" -> Math.log(num);
            case "sin" -> Math.sin(num);
            case "cos" -> Math.cos(num);
            case "tan" -> Math.tan(num);
            default -> throw new IllegalStateException("Error");
        };
    }

    // Shorthand to throw a syntax error
    private static void throwError() {
        throw new IllegalStateException("Error");
    }
}