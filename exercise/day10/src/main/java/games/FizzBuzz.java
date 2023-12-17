package games;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FizzBuzz {
    public static final int MIN = 0;
    public static final int MAX = 100;
    public static final int FIZZ = 3;
    public static final int BUZZ = 5;
    public static final int FIZZBUZZ = 15;

    private static final List<FizzBuzzRule> rules = List.of(
            new FizzBuzzRule(integer -> integer % FIZZBUZZ == 0, "FizzBuzz"),
            new FizzBuzzRule(integer -> integer % FIZZ == 0, "Fizz"),
            new FizzBuzzRule(integer -> integer % BUZZ == 0, "Buzz")
    );

    private FizzBuzz() {
    }

    public static String convert(Integer input) throws OutOfRangeException {
        return new Range(MIN, MAX).mapInRange(input)
                .map(FizzBuzz::convertSafely)
                .orElseThrow(OutOfRangeException::new);
    }

    private static String convertSafely(Integer input) {
        return rules.stream()
                .filter(fizzBuzzRule -> fizzBuzzRule.match(input))
                .findFirst()
                .map(fizzBuzzRule -> fizzBuzzRule.value)
                .orElse(input.toString());
    }

    record FizzBuzzRule(Function<Integer, Boolean> rule, String value) {
        boolean match(Integer value) {
            return this.rule.apply(value);
        }
    }

    record Range(int min, int max) {
        Optional<Integer> mapInRange(Integer value) {
            return Optional.of(value)
                    .filter(integer -> integer <= max && integer > min);
        }
    }
}
