package people;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;

public record Person(String firstName, String lastName, List<Pet> pets) {
    public Person(String firstName, String lastName) {
        this(firstName, lastName, new ArrayList<>());
    }

    public int petsMinAge() {
        return pets().stream()
                .mapToInt(Pet::age)
                .min()
                .orElse(MAX_VALUE);
    }

    public Person addPet(PetType petType, String name, int age) {
        Pet pet = new Pet(petType, name, age);
        pets.add(pet);
        return this;
    }
}
