package com.rkeeves;

import lombok.Data;

@Data
public class Team implements Comparable<Team>{

    private final String name;

    private final double value;

    @Override
    public int compareTo(Team o) {
        if (this.value < o.value) {
            return -1;
        } else if (this.value > o.value) {
            return 1;
        } else {
            return 0;
        }
    }
}
