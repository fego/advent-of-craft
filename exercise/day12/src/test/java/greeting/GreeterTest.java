package greeting;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class GreeterTest {
    @ParameterizedTest
    @CsvSource(delimiterString = "=>", textBlock = """
            NONE     => Hello.
            FORMAL   => Good evening, sir.
            CASUAL   => Sup bro?
            INTIMATE => Hello Darling!
             """)
    void sayFormally(Formality formality, String expectedGreet) {
        var greeter = new Greeter(formality);
        assertThat(greeter.greet()).isEqualTo(expectedGreet);
    }

}
