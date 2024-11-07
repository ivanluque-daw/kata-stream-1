package org.iesvdm.kata1;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Exercise5Test extends PetDomainForKata
{
    @Test
    @Tag("KATA")
    public void partitionPetAndNonPetPeople()
    {
        List<Person> partitionListPetPeople = this.people.stream().filter(Person::isPetPerson).toList();
        List<Person> partitionListNotPetPeople = this.people.stream().filter(person -> !person.isPetPerson()).toList();

        Assertions.assertEquals(7, partitionListPetPeople.size());
        Assertions.assertEquals(1, partitionListNotPetPeople.size());
    }

    @Test
    @Tag("KATA")
    @DisplayName("getOldestPet - 🐶")
    public void getOldestPet()
    {
        Pet oldestPet = this.people.stream().flatMap(person -> person.getPets().stream()).max(Comparator.comparing(Pet::getAge)).orElse(new Pet(PetType.HAMSTER, "", 0));

        Assertions.assertEquals(PetType.DOG, oldestPet.getType());
        Assertions.assertEquals(4, oldestPet.getAge());
    }

    @Test
    @Tag("KATA")
    public void getAveragePetAge()
    {
        double averagePetAge = this.people.stream().flatMap(person -> person.getPets().stream().map(Pet::getAge)).mapToInt(Integer::intValue).average().orElse(0);
        Assertions.assertEquals(1.88888, averagePetAge, 0.00001);
    }

    @Test
    @Tag("KATA")
    public void addPetAgesToExistingSet()
    {
        Set<Integer> petAges = this.people.stream().flatMap(person -> person.getPets().stream().map(Pet::getAge)).collect(Collectors.toSet());

        // var expectedSet = Set.of(1, 2, 3, 4, 5);
        var expectedSet = Set.of(1, 2, 3, 4);
        Assertions.assertEquals(expectedSet, petAges);
    }

    @Test
    @Tag("KATA")
    @DisplayName("findOwnerWithMoreThanOnePetOfTheSameType - 🐹 🐹")
    public void findOwnerWithMoreThanOnePetOfTheSameType()
    {
        Person petOwner = this.people.stream().filter(person -> person.getPetTypes().values().stream().anyMatch(count -> count > 1)).findFirst().orElse(new Person("", ""));

        Assertions.assertEquals("Harry Hamster", petOwner.getFullName());
        Assertions.assertEquals("🐹 🐹", petOwner.getPets().stream().map(pet -> pet.getType().toString()).collect(Collectors.joining(" ")));
    }
}
