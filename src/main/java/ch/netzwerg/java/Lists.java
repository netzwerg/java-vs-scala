package ch.netzwerg.java;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Lists {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("orange", "apple");
        list.set(0, "cherry"); // Lists are mutable

        // This is NOT what I mean with immutability
        List<String> unmodifiableList = Collections.unmodifiableList(list);
        // throws an UnsupportedOperationException
        unmodifiableList.set(0, "blueberry");

    }

}
