

package org.iesvdm.kata1;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.*;


public class  Exercise4Test extends PetDomainForKata
{
    @Test
    @Tag("KATA")
    public void getAgeStatisticsOfPets()
    {
        var petAges = this.people.stream().flatMap(person -> person.getPets().stream().map(Pet::getAge)).toList();

        var uniqueAges = Set.copyOf(petAges);

        var expectedSet = Set.of(1, 2, 3, 4);
        Assertions.assertEquals(expectedSet, uniqueAges);

        var stats = petAges.stream().collect(Collectors.summarizingInt(Integer::intValue));

        Assertions.assertEquals(stats.getMin(), petAges.stream().min(Integer::compareTo).get());
        Assertions.assertEquals(stats.getMax(), petAges.stream().max(Integer::compareTo).get());
        Assertions.assertEquals(stats.getSum(), petAges.stream().mapToInt(Integer::intValue).sum());
        Assertions.assertEquals(stats.getAverage(), petAges.stream().mapToInt(Integer::intValue).average().getAsDouble());
        Assertions.assertEquals(stats.getCount(), petAges.stream().count());

        Assertions.assertTrue(petAges.stream().allMatch(age -> age > 0));
        Assertions.assertFalse(petAges.stream().anyMatch(age -> age == 0));
        Assertions.assertFalse(petAges.stream().anyMatch(age -> age < 0));
    }

    @Test
    @Tag("KATA")
    @DisplayName("bobSmithsPetNamesAsString - 🐱 🐶")
    public void bobSmithsPetNamesAsString()
    {
        Person person = this.people.stream().filter(p -> p.getFullName().equals("Bob Smith")).findFirst().orElse(new Person("una", "persona"));

        String names = person.getPets().stream().map(Pet::getName).collect(Collectors.joining(" & "));
        Assertions.assertEquals("Dolly & Spot", names);
    }

    @Test
    @Tag("KATA")
    public void immutablePetCountsByEmoji()
    {
        Map<String, Long> countsByEmoji = this.people.stream().flatMap(person -> person.getPets().stream()).map(Pet::getType).collect(Collectors.groupingBy(PetType::toString, Collectors.counting()));

        Assertions.assertEquals(
                Map.of("🐱", 2L, "🐶", 2L, "🐹", 2L, "🐍", 1L, "🐢", 1L, "🐦", 1L),
                countsByEmoji
        );
    }

    /**
     * The purpose of this test is to determine the top 3 pet types.
     */
    @Test
    @Tag("KATA")
    @DisplayName("topThreePets - 🐱 🐶 🐹")
    public void topThreePets()
    {
        var favorites = this.people.stream()
                .flatMap(person -> person.getPets().stream())
                .collect(Collectors.groupingBy(Pet::getType, Collectors.counting()))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .limit(3).toList();

        Assertions.assertEquals(3, favorites.size());

        Assertions.assertTrue(favorites.contains(new AbstractMap.SimpleEntry<>(PetType.CAT, Long.valueOf(2))));
        Assertions.assertTrue(favorites.contains(new AbstractMap.SimpleEntry<>(PetType.DOG, Long.valueOf(2))));
        Assertions.assertTrue(favorites.contains(new AbstractMap.SimpleEntry<>(PetType.HAMSTER, Long.valueOf(2))));

    }

    @Test
    @Tag("KATA")
    public void getMedianOfPetAges()
    {
        var petAges = this.people.stream().flatMap(person -> person.getPets().stream().map(Pet::getAge));
        var sortedPetAges = petAges.sorted().toList();

        double median;
        if (0 == sortedPetAges.size() % 2)
        {
            // The median of a list of even numbers is the average of the two middle items
            median = (double) (sortedPetAges.get((int) sortedPetAges.size() / 2) + sortedPetAges.get(((int) sortedPetAges.size() / 2) + 1)) / 2;
        }
        else
        {
            // The median of a list of odd numbers is the middle item
            median = sortedPetAges.get(abs(sortedPetAges.size() / 2)).doubleValue();
        }

        Assertions.assertEquals(2.0, median, 0.0);
    }
}
