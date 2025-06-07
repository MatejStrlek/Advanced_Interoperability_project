package hr.algebra.advanced_interoperability_project.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MobileTest {
    @Test
    void testNoArgsConstructorAndSetters() {
        Mobile mobile = new Mobile();
        mobile.setId(1L);
        mobile.setName("iPhone 15");
        mobile.setBrand("Apple");
        mobile.setPrice(1099.99);
        mobile.setDescription("Latest iPhone model");
        mobile.setRating(4.8);

        assertEquals(1L, mobile.getId());
        assertEquals("iPhone 15", mobile.getName());
        assertEquals("Apple", mobile.getBrand());
        assertEquals(1099.99, mobile.getPrice());
        assertEquals("Latest iPhone model", mobile.getDescription());
        assertEquals(4.8, mobile.getRating());
    }

    @Test
    void testAllArgsConstructorWithoutId() {
        Mobile mobile = new Mobile("Galaxy S24", "Samsung", 899.99, "Flagship Android", 4.6);

        assertNull(mobile.getId());
        assertEquals("Galaxy S24", mobile.getName());
        assertEquals("Samsung", mobile.getBrand());
        assertEquals(899.99, mobile.getPrice());
        assertEquals("Flagship Android", mobile.getDescription());
        assertEquals(4.6, mobile.getRating());
    }

    @Test
    void testAllArgsConstructorWithId() {
        Mobile mobile = new Mobile(100L, "OnePlus 12", "OnePlus", 749.99, "Fast and smooth", 4.4);

        assertEquals(100L, mobile.getId());
        assertEquals("OnePlus 12", mobile.getName());
        assertEquals("OnePlus", mobile.getBrand());
        assertEquals(749.99, mobile.getPrice());
        assertEquals("Fast and smooth", mobile.getDescription());
        assertEquals(4.4, mobile.getRating());
    }
}