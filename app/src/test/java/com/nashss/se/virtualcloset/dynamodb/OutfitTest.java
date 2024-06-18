package com.nashss.se.virtualcloset.dynamodb;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OutfitTest {

    @Test
    public void testEquals_sameObject_returnsTrue() {
        Outfit outfit = new Outfit();
        assertTrue(outfit.equals(outfit));
    }

    @Test
    public void testEquals_equalObjects_returnsTrue() {
        Outfit outfit1 = createSampleOutfit();
        Outfit outfit2 = createSampleOutfit();

        assertTrue(outfit1.equals(outfit2));
        assertTrue(outfit2.equals(outfit1));
    }

    @Test
    public void testEquals_differentObjects_returnsFalse() {
        Outfit outfit1 = createSampleOutfit();
        Outfit outfit2 = createSampleOutfit();
        outfit2.setId("differentId");

        assertFalse(outfit1.equals(outfit2));
        assertFalse(outfit2.equals(outfit1));
    }

    @Test
    public void testEquals_null_returnsFalse() {
        Outfit outfit = new Outfit();
        assertFalse(outfit.equals(null));
    }

    @Test
    public void testHashCode_equalObjects_sameHashCode() {
        Outfit outfit1 = createSampleOutfit();
        Outfit outfit2 = createSampleOutfit();

        assertEquals(outfit1.hashCode(), outfit2.hashCode());
    }

    @Test
    public void testHashCode_differentObjects_differentHashCode() {
        Outfit outfit1 = createSampleOutfit();
        Outfit outfit2 = createSampleOutfit();
        outfit2.setId("differentId");

        assertNotEquals(outfit1.hashCode(), outfit2.hashCode());
    }

    private Outfit createSampleOutfit() {
        Outfit outfit = new Outfit();
        outfit.setId("1");
        outfit.setName("Test Outfit");
        outfit.setCustomerId("customer123");
        outfit.setTags(new HashSet<>(List.of("tag1", "tag2")));
        outfit.setClothingItems(new ArrayList<>());
        outfit.setWornCount(5);
        return outfit;
    }

}