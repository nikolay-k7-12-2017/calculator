package calculator;

import java.util.Comparator;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Calculator {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
      printOperation();

    }


    private static void printOperation(){
        Integer numberOut=0;
        Integer number1=enterNumber();
        String d=enterOperation();
        Integer number2=enterNumber();
        if (d.equals("*")) {
            Comparator<Integer> comparator = (o1, o2) -> o1 * o2;
            numberOut=comparator.compare(number1, number2);
        }
        if (d.equals("-")) {
            Comparator<Integer> comparator = (o1, o2) -> o1 - o2;
            numberOut=comparator.compare(number1, number2);
        }
        if (d.equals("+")) {
            Comparator<Integer> comparator = (o1, o2) -> o1 + o2;
            numberOut=comparator.compare(number1, number2);
        }
        System.out.println("Answer: "+number1+" "+d+" "+number2+" = "+numberOut);
    }

    private static String enterOperation() {
        System.out.println("Select operation(*,+,-): ");
        String g =scanner.nextLine();
        Predicate<String> stFu=
        (s)->s.matches("[*+-]");
        if (stFu.test(g)) {
          //  System.out.println(g);
        }else {
            System.out.println("Error enter:");
            enterOperation();
        }
       return g;
        }


        private static Integer enterNumber(){
        System.out.println("Enter number: ");
        BiFunction<Supplier<String>,
                BiFunction<String, Predicate<String>, Integer>,
                Integer>
                startFunc = (scan, func) ->{
            return func.apply(scan.get(),
                    (s)->s.matches("[0-9]+"));
        };

        return startFunc.apply(
                scanner::nextLine,
                (string, digitPredicate) -> {
                    if (digitPredicate.test(string)) {
                        return Integer.parseInt(string);
                    } else {
                        Supplier<Integer> supplier =
                                () -> {
                                    System.out.println("Error! Proceed? " +
                                            "(y-try again, " +
                                            "n- exit, " +
                                            "p- continue with 0)");
                                    final String dess = scanner.nextLine();

                                    BiFunction<Predicate<String>, Supplier<Integer>, Integer> function =
                                            (predicate, supplier1) -> {
                                                Integer res = 0;

                                                if (predicate.test(dess)) {
                                                    res = supplier1.get();
                                                }

                                                return res;
                                            };

                                    return function.apply((s) -> "y".equals(s), Calculator::enterNumber);
                                };
                        return supplier.get();
                    }
                });
    }
}
