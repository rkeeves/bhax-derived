package com.rkeeves;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CollectionsPlusTests {

    @Test(dataProvider = "collectionsToSortDataProvider")
    public void testOrderShouldReturnExpectedListWhenCollectionIsPassed(Collection<Integer> input, List<Integer> expectedOutput) {
        // Given as parameters

        // When
        List<Integer> actualOutput = CollectionsPlus.toAscendingList(input);

        // Then
        assertThat(actualOutput, equalTo(expectedOutput));
    }

    @DataProvider
    private Object[][] collectionsToSortDataProvider() {
        return new Object[][]{
                {Collections.emptySet(), Collections.emptyList()},
                {Set.of(1), List.of(1)},
                {Set.of(2, 1), List.of(1, 2)}
        };
    }

    @Data
    static class NotImplementingComparable {

        public final int v;

    }

    @Test(dataProvider = "collectionsToSortDataProviderForNotImplementingComparable")
    public void testOrderShouldReturnExpectedListWhenCollectionIsPassedComparator(Collection<NotImplementingComparable> input, List<NotImplementingComparable> expectedOutput) {
        // Given as parameters

        // When
        List<NotImplementingComparable> actualOutput = CollectionsPlus.toAscendingList(input, (a, b) -> a.v - b.v);

        // Then
        assertThat(actualOutput, equalTo(expectedOutput));
    }


    @DataProvider
    private Object[][] collectionsToSortDataProviderForNotImplementingComparable() {
        return new Object[][]{
                {Collections.emptySet(), Collections.emptyList()},
                {Set.of(new NotImplementingComparable(1)), List.of(new NotImplementingComparable(1))},
                {Set.of(new NotImplementingComparable(2), new NotImplementingComparable(1)), List.of(new NotImplementingComparable(1), new NotImplementingComparable(2))}
        };
    }
}
