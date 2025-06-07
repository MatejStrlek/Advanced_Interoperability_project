package hr.algebra.advanced_interoperability_project.mapper;

import hr.algebra.advanced_interoperability_project.domain.Mobile;
import hr.algebra.advanced_interoperability_project.dto.MobileDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MobileMapperTest {
    @Test
    void toDTO_shouldMapCorrectly() {
        Mobile mobile = new Mobile(1L, "Galaxy S24", "Samsung", 899.99, "Flagship phone", 4.6);

        MobileDTO dto = MobileMapper.toDTO(mobile);

        assertEquals("Galaxy S24", dto.getName());
        assertEquals("Samsung", dto.getBrand());
        assertEquals(899.99, dto.getPrice());
        assertEquals("Flagship phone", dto.getDescription());
        assertEquals(4.6, dto.getRating());
    }

    @Test
    void toEntity_shouldMapCorrectly() {
        MobileDTO dto = new MobileDTO("iPhone 15", "Apple", 1099.99, "Latest iPhone", 4.8);

        Mobile mobile = MobileMapper.toEntity(dto);

        assertEquals("iPhone 15", mobile.getName());
        assertEquals("Apple", mobile.getBrand());
        assertEquals(1099.99, mobile.getPrice());
        assertEquals("Latest iPhone", mobile.getDescription());
        assertEquals(4.8, mobile.getRating());
    }

    @Test
    void updateEntity_shouldCopyAllFields() {
        Mobile mobile = new Mobile(1L, "Old Name", "Old Brand", 500.0, "Old desc", 3.0);
        MobileDTO dto = new MobileDTO("New Name", "New Brand", 750.0, "New desc", 4.5);

        MobileMapper.updateEntity(dto, mobile);

        assertEquals("New Name", mobile.getName());
        assertEquals("New Brand", mobile.getBrand());
        assertEquals(750.0, mobile.getPrice());
        assertEquals("New desc", mobile.getDescription());
        assertEquals(4.5, mobile.getRating());
    }

    @Test
    void constructor_shouldBePrivate() throws Exception {
        var constructor = MobileMapper.class.getDeclaredConstructor();
        assertFalse(constructor.canAccess(null));

        constructor.setAccessible(true);
        MobileMapper instance = constructor.newInstance();

        assertNotNull(instance);
    }
}