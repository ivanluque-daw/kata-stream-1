package org.iesvdm.kata1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Exercise1Test extends PetDomainForKata
{
    @Test
    @Tag("KATA")
    public void getFirstNamesOfAllPeople()
    {
        List<String> firstNames = this.people.stream().map(Person::getFirstName).toList();

        var expectedFirstNames = Arrays.asList("Mary", "Bob", "Ted", "Jake", "Barry", "Terry", "Harry", "John");
        Assertions.assertIterableEquals(expectedFirstNames, firstNames);
    }

    @Test
    @Tag("KATA")
    public void getNamesOfMarySmithsPets()
    {
        Optional<Person> optionalPerson = this.getPersonNamed("Mary Smith");
        List<String> names = new ArrayList<>();

        if (optionalPerson.isPresent()) {
            List<Pet> pets = optionalPerson.get().getPets();

            names = pets.stream().map(Pet::getName).toList();
        }

        var expectedPetsNames = List.of("Tabby");
        Assertions.assertIterableEquals(expectedPetsNames, names);
    }

    @Test
    @Tag("KATA")
    @DisplayName("getPeopleWithCats 🐱")
    public void getPeopleWithCats()
    {
        List<String> peopleWithCats = this.people.stream()
                .filter(person -> person.hasPet(PetType.CAT))
                .map(Person::getLastName).toList();

        var expectedFirstNames = Arrays.asList("Smith", "Smith");

        Assertions.assertEquals(expectedFirstNames, peopleWithCats);
    }

    @Test
    @Tag("KATA")
    @DisplayName("getPeopleWithoutCats 🐱")
    public void getPeopleWithoutCats()
    {
        List<String> peopleWithoutCats = this.people.stream()
                .filter(person -> !person.hasPet(PetType.CAT))
                .map(Person::getLastName).toList();

        var expectedFirstNames = Arrays.asList("Smith", "Snake", "Bird", "Turtle", "Hamster", "Doe");
        Assertions.assertIterableEquals(expectedFirstNames, peopleWithoutCats);
    }
}
