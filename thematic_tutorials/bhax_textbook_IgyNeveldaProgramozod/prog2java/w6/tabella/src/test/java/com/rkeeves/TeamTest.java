package com.rkeeves;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamTest {

    static Map<Double,String> valueToTeamNameMap = new HashMap<>();

    static List<String> teamNamesBeforeSort = Arrays.asList("A", "B", "C");

    static List<Double> teamValuesBeforeSort = Arrays.asList(2., 3., 1.);

    static List<String> teamNamesAfterSort;

    static List<Double> teamValuesAfterSort;

    @BeforeAll
    public static void init(){
        IntStream
                .range(0,teamNamesBeforeSort.size())
                .forEach(i->valueToTeamNameMap.put(teamValuesBeforeSort.get(i),teamNamesBeforeSort.get(i)));
        teamValuesAfterSort = teamValuesBeforeSort
                .stream()
                .sorted()
                .collect(Collectors.toList());
        teamNamesAfterSort = teamValuesAfterSort
                .stream()
                .map(valueToTeamNameMap::get)
                .collect(Collectors.toList());
    }

    @Test
    void test(){
        // given
        List<Team> teamsExpected = IntStream
                .range(0,teamNamesBeforeSort.size())
                .mapToObj(i->new Team(teamNamesAfterSort.get(i),teamValuesAfterSort.get(i)))
                .collect(Collectors.toList());
        // when
        List<Team> teams = IntStream
                .range(0, teamNamesBeforeSort.size())
                .mapToObj(i -> new Team(teamNamesBeforeSort.get(i), teamValuesBeforeSort.get(i)))
                .sorted()
                .collect(Collectors.toList());
        // then
        assertEquals(teams,teamsExpected);
    }
}
