package people;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.lineSeparator;
import static java.util.Comparator.comparingInt;

public record Population(List<Person> people) {

    public String format() {

        return people().stream()
                .map(Population::formatOnePerson)
                .collect(Collectors.joining(lineSeparator()));
    }

    private static String formatOnePerson(Person person) {
        StringBuilder response = new StringBuilder();
        response.append(String.format("%s %s", person.firstName(), person.lastName()));

        if (!person.pets().isEmpty()) {
            response.append(" who owns : ");
            response.append(petNames(person));
        }

        return response.toString();
    }

    public static String petNames(Person person) {
        String names = person.pets().stream()
                .map(Pet::name)
                .collect(Collectors.joining(" "));
        names += " ";
        return names;
    }

    public Person youngest() {
        return people().stream()
                .min(comparingInt(Person::youngestPetAge))
                .orElse(null);
    }

}
