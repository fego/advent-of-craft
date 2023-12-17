import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

public class PasswordTest {

    @ParameterizedTest(name = "{1}")
    @CsvSource(useHeadersInDisplayName = true, delimiter = '|',
            textBlock = """
                    Password  | Rule
                    Ee4#76yh  | 8 characters password with # special character
                    Ee4@76yh  | 8 characters password with @ special character
                    Ee4.76yh  | 8 characters password with @ special character
                    Ee4$76yh  | 8 characters password with @ special character
                    Ee4%76yh  | 8 characters password with @ special character
                    Ee4&76yh  | 8 characters password with & special character
                    Ee4%76yhI | 9 characters password\s
                     """)
    public void valid_passord(String password, String rule) {
        Optional<Password> optionalPassword = Password.from(password);
        Assertions.assertThat(optionalPassword).isPresent();
    }

    @ParameterizedTest(name = "{1}")
    @CsvSource(useHeadersInDisplayName = true, delimiter = '|',
            textBlock = """
                    Password | Rule
                    e4#76yh  | A password should have at least 8 characters
                    ee4#76yh | A password should have at least one capital letter
                    EE4#76YH | A password should have at least one lowercase letter
                    eEE#ZAYH | A password should have at least one number
                    eEET5AYH | A password should have at least one character in . * # @ $ % &
                    """)
    void invalid_password(String actualPassword, String reason) {
        Optional<Password> password = Password.from(actualPassword);
        Assertions.assertThat(password).as(reason).isEmpty();
    }

}
