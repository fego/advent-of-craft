import food.Food;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

import static java.time.LocalDate.of;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class EdibleTests {
    private static final LocalDate expirationDate = of(2023, 12, 1);
    private static final UUID inspector = randomUUID();
    private static final LocalDate notFreshDate = expirationDate.plusDays(7);
    private static final LocalDate freshDate = expirationDate.minusDays(7);

    public static Stream<Arguments> notEdibleFood() {
        return Stream.of(
                Arguments.of(true, inspector, notFreshDate, "Not fresh food"),
                Arguments.of(false, inspector, freshDate, "Not approved for consumption"),
                Arguments.of(true, null, freshDate, "No inspector"),
                Arguments.of(false, null, notFreshDate, "Not approved for consumption, no inspector and not fresh food"),
                Arguments.of(false, null, freshDate, "Not approved for consumption, no inspector")
        );
    }

    @DisplayName("Not edible food")
    @ParameterizedTest(name = "{3}")
    @MethodSource("notEdibleFood")
    void not_edible_if_not_fresh(boolean approvedForConsumption, UUID inspectorId, LocalDate now, String doc) {
        var food = new Food(
                expirationDate,
                approvedForConsumption,
                inspectorId);

        assertThat(food.isEdible(() -> now)).as(doc).isFalse();
    }

    @Test
    void edible_food() {
        var food = new Food(
                expirationDate,
                true,
                inspector);

        assertThat(food.isEdible(() -> freshDate)).isTrue();
    }
}