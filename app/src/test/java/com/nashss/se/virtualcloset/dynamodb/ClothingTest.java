package com.nashss.se.virtualcloset.dynamodb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClothingTest {
    @Test
    public void testEquals_sameObject_returnsTrue() {
        Clothing clothing = new Clothing();
        assertTrue(clothing.equals(clothing));
    }

    @Test
    public void testEquals_equalObjects_returnsTrue() {
        Clothing clothing1 = new Clothing();
        clothing1.setClothingId("1");
        clothing1.setCustomerId("customerId");
        clothing1.setWornCount(5);
        clothing1.setCategory("category");
        clothing1.setColor("color");
        clothing1.setFit("fit");
        clothing1.setLength("length");
        clothing1.setWeather("weather");
        clothing1.setOccasion("occasion");

        Clothing clothing2 = new Clothing();
        clothing2.setClothingId("1");
        clothing2.setCustomerId("customerId");
        clothing2.setWornCount(5);
        clothing2.setCategory("category");
        clothing2.setColor("color");
        clothing2.setFit("fit");
        clothing2.setLength("length");
        clothing2.setWeather("weather");
        clothing2.setOccasion("occasion");

        assertTrue(clothing1.equals(clothing2));
        assertTrue(clothing2.equals(clothing1));
    }

    @Test
    public void testEquals_differentObjects_returnsFalse() {
        Clothing clothing1 = new Clothing();
        clothing1.setClothingId("1");

        Clothing clothing2 = new Clothing();
        clothing2.setClothingId("2");

        assertFalse(clothing1.equals(clothing2));
        assertFalse(clothing2.equals(clothing1));
    }

    @Test
    public void testEquals_null_returnsFalse() {
        Clothing clothing = new Clothing();
        assertFalse(clothing.equals(null));
    }

    @Test
    public void testHashCode_equalObjects_sameHashCode() {
        Clothing clothing1 = new Clothing();
        clothing1.setClothingId("1");
        clothing1.setCustomerId("customerId");
        clothing1.setWornCount(5);
        clothing1.setCategory("category");
        clothing1.setColor("color");
        clothing1.setFit("fit");
        clothing1.setLength("length");
        clothing1.setWeather("weather");
        clothing1.setOccasion("occasion");

        Clothing clothing2 = new Clothing();
        clothing2.setClothingId("1");
        clothing2.setCustomerId("customerId");
        clothing2.setWornCount(5);
        clothing2.setCategory("category");
        clothing2.setColor("color");
        clothing2.setFit("fit");
        clothing2.setLength("length");
        clothing2.setWeather("weather");
        clothing2.setOccasion("occasion");

        assertEquals(clothing1.hashCode(), clothing2.hashCode());
    }

    @Test
    public void testHashCode_differentObjects_differentHashCode() {
        Clothing clothing1 = new Clothing();
        clothing1.setClothingId("1");

        Clothing clothing2 = new Clothing();
        clothing2.setClothingId("2");

        assertNotEquals(clothing1.hashCode(), clothing2.hashCode());
    }

}