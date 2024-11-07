package org.iesvdm.kata1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Exercise2Test extends PetDomainForKata
{
    @Test
    @Tag("KATA")
    @DisplayName("doAnyPeopleHaveCats 🐱?")
    public void doAnyPeopleHaveCats()
    {
        Predicate<Person> predicate = p -> p.getPets().stream().anyMatch(pet -> pet.getType().equals(PetType.CAT));
        Assertions.assertTrue(this.people.stream().anyMatch(predicate));
    }

    @Test
    @Tag("KATA")
    public void doAllPeopleHavePets()
    {
        Predicate<Person> predicate = Person::isPetPerson;
        Assertions.assertFalse(this.people.stream().allMatch(predicate));
    }

    @Test
    @Tag("KATA")
    @DisplayName("howManyPeopleHaveCats 🐱?")
    public void howManyPeopleHaveCats()
    {
        int count = this.people.stream().filter(person -> person.hasPet(PetType.CAT)).collect(Collectors.counting()).intValue();
        Assertions.assertEquals(2, count);
    }

    @Test
    @Tag("KATA")
    public void findMarySmith()
    {
        Person result = this.people.stream().filter(person -> person.getFullName().equals("Mary Smith")).findFirst().orElse(new Person("", ""));

        Assertions.assertEquals("Mary", result.getFirstName());
        Assertions.assertEquals("Smith", result.getLastName());
    }

    @Test
    @Tag("KATA")
    @DisplayName("findPetNamedSerpy 🐍")
    public void findPetNamedSerpy()
    {
        List<Pet> petList = this.people.stream().flatMap(person -> person.getPets().stream()).toList();
        Pet serpySnake = petList.stream().filter(pet -> pet.getName().equals("Serpy")).findFirst().orElse(new Pet(PetType.BIRD,"", 0));

        Assertions.assertEquals("🐍", serpySnake.getType().toString());
    }

    @Test
    @Tag("KATA")
    public void getPeopleWithPets()
    {
        List<Person> petPeople = this.people.stream().filter(person -> person.isPetPerson()).toList();
        Assertions.assertEquals(7, petPeople.size());
    }

    @Test
    @Tag("KATA")
    public void getAllPetTypesOfAllPeople()
    {
        Function<Person, Map<PetType, Long>> function = Person::getPetTypes;
        Set<PetType> petTypes = this.people.stream().map(function).flatMap(map -> map.keySet().stream()).collect(Collectors.toSet());

        var expectedSet = Set.of(PetType.CAT, PetType.DOG, PetType.TURTLE, PetType.HAMSTER, PetType.BIRD, PetType.SNAKE);
        Assertions.assertEquals(expectedSet, petTypes);
    }

    @Test
    @Tag("KATA")
    public void getAllPetEmojisOfAllPeople()
    {
        Function<Person, Map<String, Long>> function = Person::getPetEmojis;
        Set<String> petEmojis = this.people.stream().map(function).flatMap(map -> map.keySet().stream()).collect(Collectors.toSet());

        var expected = Set.of("🐱", "🐶", "🐢", "🐹", "🐦", "🐍");
        Assertions.assertEquals(expected, petEmojis);
    }

    @Test
    @Tag("KATA")
    public void getFirstNamesOfAllPeople()
    {
        List<String> firstNames = this.people.stream().map(Person::getFirstName).toList();

        var expectedList = List.of("Mary", "Bob", "Ted", "Jake", "Barry", "Terry", "Harry", "John");
        Assertions.assertEquals(expectedList, firstNames);
    }

    @Test
    @Tag("KATA")
    @DisplayName("doAnyPeopleHaveCatsRefactor 🐱?")
    public void doAnyPeopleHaveCatsRefactor()
    {
        boolean peopleHaveCatsLambda = this.people.stream().anyMatch(person -> person.hasPet(PetType.CAT));
        Assertions.assertTrue(peopleHaveCatsLambda);

    }

    @Test
    @Tag("KATA")
    @DisplayName("doAllPeopleHaveCatsRefactor 🐱?")
    public void doAllPeopleHaveCatsRefactor()
    {
        boolean peopleHaveCats = this.people.stream().allMatch(person -> person.hasPet(PetType.CAT));
        Assertions.assertFalse(peopleHaveCats);
    }

    @Test
    @Tag("KATA")
    @DisplayName("getPeopleWithCatsRefactor 🐱?")
    public void getPeopleWithCatsRefactor()
    {
        List<Person> peopleWithCats = this.people.stream().filter(person -> person.hasPet(PetType.CAT)).toList();
        Assertions.assertEquals(2, peopleWithCats.size());
    }

    @Test
    @Tag("KATA")
    @DisplayName("getPeopleWithoutCatsRefactor 🐱?")
    public void getPeopleWithoutCatsRefactor()
    {
        List<Person> peopleWithoutCats = this.people.stream().filter(person -> !person.hasPet(PetType.CAT)).toList();
        Assertions.assertEquals(6, peopleWithoutCats.size());
    }
}
