package hr.algebra.advanced_interoperability_project.service;

import hr.algebra.advanced_interoperability_project.domain.Mobile;
import hr.algebra.advanced_interoperability_project.dto.MobileDTO;
import hr.algebra.advanced_interoperability_project.repository.MobileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MobileServiceImplTest {
    @Mock
    private MobileRepository mobileRepository;

    @InjectMocks
    private MobileServiceImpl mobileService;

    private Mobile mobile;
    private MobileDTO mobileDTO;

    @BeforeEach
    void setUp() {
        mobile = new Mobile(1L, "Pixel 8", "Google", 799.99, "Fast Android", 4.5);
        mobileDTO = new MobileDTO("Pixel 8", "Google", 799.99, "Fast Android", 4.5);
    }

    @Test
    void getAllMobiles_shouldReturnMappedDTOs() {
        when(mobileRepository.findAll()).thenReturn(List.of(mobile));
        List<MobileDTO> result = mobileService.getAllMobiles();

        assertEquals(1, result.size());
        assertEquals("Pixel 8", result.get(0).getName());
        assertEquals("Google", result.get(0).getBrand());
        assertEquals(799.99, result.get(0).getPrice());
        assertEquals("Fast Android", result.get(0).getDescription());
        assertEquals(4.5, result.get(0).getRating());
    }

    @Test
    void getMobileById_shouldReturnDT() {
        when(mobileRepository.findById(1L)).thenReturn(Optional.of(mobile));
        Optional<MobileDTO> result = mobileService.getMobileById(1L);
        assertTrue(result.isPresent());
        assertEquals("Pixel 8", result.get().getName());
    }

    @Test
    void getMobileById_shouldReturnEmpty() {
        when(mobileRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<MobileDTO> result = mobileService.getMobileById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void saveMobile_shouldCallRepositoryWithEntity() {
        mobileService.saveMobile(mobileDTO);
        verify(mobileRepository, times(1)).save(any(Mobile.class));
    }

    @Test
    void updateMobile_shouldUpdateEntity() {
        when(mobileRepository.findById(1L)).thenReturn(Optional.of(mobile));
        mobileService.updateMobile(1L, mobileDTO);
        verify(mobileRepository).save(any(Mobile.class));
    }

    @Test
    void updateMobile_shouldNotSave() {
        when(mobileRepository.findById(1L)).thenReturn(Optional.empty());
        mobileService.updateMobile(1L, mobileDTO);
        verify(mobileRepository, never()).save(any());
    }

    @Test
    void deleteMobile_shouldCallDeleteById() {
        mobileService.deleteMobile(1L);
        verify(mobileRepository, times(1)).deleteById(1L);
    }
}