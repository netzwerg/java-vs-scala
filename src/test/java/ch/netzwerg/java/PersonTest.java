package ch.netzwerg.java;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersonTest {

    private static final String NAME = "Ada";

    @Test
    public void construction() {
        Person person = new Person(NAME);
        assertEquals(NAME, person.getName());
        assertEquals(person, new Person(NAME));
    }

}