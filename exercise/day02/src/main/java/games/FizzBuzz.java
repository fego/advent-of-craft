package games;

public class FizzBuzz {

    public static final int BUZZ = 5;
    public static final int FIZZ = 3;
    public static final int MIN_RANGE = 0;
    public static final int MAX_RANGE = 100;

    private FizzBuzz() {
    }

    public static String convert(Integer input) throws OutOfRangeException {
        if (isOutOfRange(input)) {
            throw new OutOfRangeException();
        }
        if (is(FIZZ, input) && is(BUZZ, input)) {
            return "FizzBuzz";
        }
        if (is(FIZZ, input)) {
            return "Fizz";
        }
        if (is(BUZZ, input)) {
            return "Buzz";
        }
        return input.toString();
    }

    private static boolean isOutOfRange(Integer input) {
        return input <= MIN_RANGE || input > MAX_RANGE;
    }

    private static boolean is(int multiple, Integer input) {
        return input % multiple == 0;
    }

}
