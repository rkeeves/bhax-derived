package com.rkeeves;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionsPlus {

    public static <T extends Comparable<T>> List<T> toAscendingList(Collection<T> coll){
        return coll.stream().sorted().collect(Collectors.toList());
    }

    public static <T> List<T> toAscendingList(Collection<T> coll, Comparator<T> cmp){
        return coll.stream().sorted(cmp).collect(Collectors.toList());
    }
}
