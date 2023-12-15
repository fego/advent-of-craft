import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import people.Person;
import people.PetType;
import people.Population;

import java.util.Arrays;
import java.util.List;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

class PopulationTests {
    private static Population population;

    @BeforeAll
    static void setup() {
        List<Person> people = Arrays.asList(
                new Person("Peter", "Griffin")
                        .addPet(PetType.CAT, "Tabby", 2),
                new Person("Stewie", "Griffin")
                        .addPet(PetType.CAT, "Dolly", 3)
                        .addPet(PetType.DOG, "Brian", 9),
                new Person("Joe", "Swanson")
                        .addPet(PetType.DOG, "Spike", 4),
                new Person("Lois", "Griffin")
                        .addPet(PetType.SNAKE, "Serpy", 1),
                new Person("Meg", "Griffin")
                        .addPet(PetType.BIRD, "Tweety", 1),
                new Person("Chris", "Griffin")
                        .addPet(PetType.TURTLE, "Speedy", 4),
                new Person("Cleveland", "Brown")
                        .addPet(PetType.HAMSTER, "Fuzzy", 1)
                        .addPet(PetType.HAMSTER, "Wuzzy", 2),
                new Person("Glenn", "Quagmire")
        );
        population = new Population(people);
    }

    @Test
    void peopleWithTheirPets() {
        final var response = population.format();

        assertThat(response)
                .isEqualTo("Peter Griffin who owns : Tabby " + lineSeparator() +
                        "Stewie Griffin who owns : Dolly Brian " + lineSeparator() +
                        "Joe Swanson who owns : Spike " + lineSeparator() +
                        "Lois Griffin who owns : Serpy " + lineSeparator() +
                        "Meg Griffin who owns : Tweety " + lineSeparator() +
                        "Chris Griffin who owns : Speedy " + lineSeparator() +
                        "Cleveland Brown who owns : Fuzzy Wuzzy " + lineSeparator() +
                        "Glenn Quagmire");
    }

    @Test
    void whoOwnsTheYoungestPet() {
        var filtered = population.youngest();

        assert filtered != null;
        assertThat(filtered.firstName()).isEqualTo("Lois");
    }

}
