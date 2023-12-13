package games;

public class FizzBuzz {
    private FizzBuzz() {
    }

    public static String convert(Integer input) throws OutOfRangeException {
        if (input <= 0 || input > 100) {
            throw new OutOfRangeException();
        }
        if (isMultipleOf(3, input) && isMultipleOf(5, input)) {
            return "FizzBuzz";
        }
        if (isMultipleOf(3, input)) {
            return "Fizz";
        }
        if (isMultipleOf(5, input)) {
            return "Buzz";
        }
        return input.toString();
    }

    private static boolean isMultipleOf(int multiple, Integer input) {
        return input % multiple == 0;
    }

}
