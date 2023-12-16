package games;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FizzBuzzTests {
    @ParameterizedTest
    @CsvSource(delimiterString = "=>", useHeadersInDisplayName = true, textBlock = """
            Actual number to convert => Expected Conversion
            1  => 1
            67 => 67
            82 => 82
            3  => Fizz
            66 => Fizz
            99 => Fizz
            5  => Buzz
            50 => Buzz
            15 => FizzBuzz
            30 => FizzBuzz
            45 => FizzBuzz
            """)
    void when_fizz_buzz_then_is_converted(Integer actualNumberToConvert, String expectedConversion) throws OutOfRangeException {
        assertThat(FizzBuzz.convert(actualNumberToConvert)).isEqualTo(expectedConversion);
    }

    @ParameterizedTest(name = "When convert {0} then throw OutOfRangeException")
    @ValueSource(ints = {0, 101, -1})
    void throws_an_exception_for(int actualToConvert) {
        assertThatThrownBy(() -> FizzBuzz.convert(actualToConvert))
                .isInstanceOf(OutOfRangeException.class);
    }

}