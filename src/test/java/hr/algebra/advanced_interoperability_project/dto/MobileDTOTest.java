package hr.algebra.advanced_interoperability_project.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MobileDTOTest {
    @Test
    void testNoArgsConstructorAndSetters() {
        MobileDTO dto = new MobileDTO();

        dto.setName("Pixel 8");
        dto.setBrand("Google");
        dto.setPrice(799.99);
        dto.setDescription("Latest Google phone");
        dto.setRating(4.5);

        assertEquals("Pixel 8", dto.getName());
        assertEquals("Google", dto.getBrand());
        assertEquals(799.99, dto.getPrice());
        assertEquals("Latest Google phone", dto.getDescription());
        assertEquals(4.5, dto.getRating());
    }

    @Test
    void testAllArgsConstructor() {
        MobileDTO dto = new MobileDTO(
                "iPhone 15",
                "Apple",
                1099.99,
                "New iPhone",
                4.8
        );

        assertEquals("iPhone 15", dto.getName());
        assertEquals("Apple", dto.getBrand());
        assertEquals(1099.99, dto.getPrice());
        assertEquals("New iPhone", dto.getDescription());
        assertEquals(4.8, dto.getRating());
    }
}