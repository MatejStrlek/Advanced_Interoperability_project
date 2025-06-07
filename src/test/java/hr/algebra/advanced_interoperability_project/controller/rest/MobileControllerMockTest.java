package hr.algebra.advanced_interoperability_project.controller.rest;

import hr.algebra.advanced_interoperability_project.controller.MobileController;
import hr.algebra.advanced_interoperability_project.dto.MobileDTO;
import hr.algebra.advanced_interoperability_project.service.MobileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MobileControllerMockTest {
    @Mock
    private MobileService mobileService;

    @InjectMocks
    private MobileController mobileController;

    private MobileDTO sampleMobile;

    @BeforeEach
    void setUp() {
        sampleMobile = new MobileDTO();
        sampleMobile.setName("Pixel 8");
    }

    @Test
    void getAllMobiles_returnsList() {
        when(mobileService.getAllMobiles()).thenReturn(List.of(sampleMobile));

        ResponseEntity<List<MobileDTO>> response = mobileController.getAllMobiles();

        assertEquals(200, response.getStatusCodeValue());
        Assertions.assertNotNull(response.getBody());
        assertEquals("Pixel 8", response.getBody().get(0).getName());
    }

    @Test
    void getAllMobiles_returnsNoContent() {
        when(mobileService.getAllMobiles()).thenReturn(List.of());

        ResponseEntity<List<MobileDTO>> response = mobileController.getAllMobiles();

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void getMobileById_returnsMobile() {
        when(mobileService.getMobileById(1L)).thenReturn(Optional.of(sampleMobile));

        ResponseEntity<MobileDTO> response = mobileController.getMobileById(1L);

        assertEquals(200, response.getStatusCodeValue());
        Assertions.assertNotNull(response.getBody());
        assertEquals("Pixel 8", response.getBody().getName());
    }

    @Test
    void getMobileById_returnsNotFound() {
        when(mobileService.getMobileById(1L)).thenReturn(Optional.empty());

        ResponseEntity<MobileDTO> response = mobileController.getMobileById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void saveMobile_success() {
        doNothing().when(mobileService).saveMobile(sampleMobile);

        ResponseEntity<String> response = mobileController.saveMobile(sampleMobile);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Mobile has been saved", response.getBody());
    }

    @Test
    void saveMobile_exceptionThrown_returnsBadRequest() {
        doThrow(new RuntimeException("Save failed")).when(mobileService).saveMobile(sampleMobile);

        ResponseEntity<String> response = mobileController.saveMobile(sampleMobile);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Save failed", response.getBody());
    }

    @Test
    void updateMobile_success() {
        when(mobileService.getMobileById(1L)).thenReturn(Optional.of(sampleMobile));
        doNothing().when(mobileService).updateMobile(eq(1L), eq(sampleMobile));

        ResponseEntity<String> response = mobileController.updateMobile(1L, sampleMobile);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Mobile with id 1 has been updated", response.getBody());
    }

    @Test
    void updateMobile_notFoundInitialCheck() {
        when(mobileService.getMobileById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = mobileController.updateMobile(1L, sampleMobile);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void updateMobile_throwsNoSuchElement_returnsNotFound() {
        when(mobileService.getMobileById(1L)).thenReturn(Optional.of(sampleMobile));
        doThrow(NoSuchElementException.class).when(mobileService).updateMobile(1L, sampleMobile);

        ResponseEntity<String> response = mobileController.updateMobile(1L, sampleMobile);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void updateMobile_otherException_returnsBadRequest() {
        when(mobileService.getMobileById(1L)).thenReturn(Optional.of(sampleMobile));
        doThrow(new RuntimeException("Update failed")).when(mobileService).updateMobile(1L, sampleMobile);

        ResponseEntity<String> response = mobileController.updateMobile(1L, sampleMobile);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Update failed", response.getBody());
    }

    @Test
    void deleteMobile_success() {
        when(mobileService.getMobileById(1L)).thenReturn(Optional.of(sampleMobile));
        doNothing().when(mobileService).deleteMobile(1L);

        ResponseEntity<String> response = mobileController.deleteMobile(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Mobile with id 1 has been deleted", response.getBody());
    }

    @Test
    void deleteMobile_notFoundInitialCheck() {
        when(mobileService.getMobileById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = mobileController.deleteMobile(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deleteMobile_throwsNoSuchElement_returnsNotFound() {
        when(mobileService.getMobileById(1L)).thenReturn(Optional.of(sampleMobile));
        doThrow(NoSuchElementException.class).when(mobileService).deleteMobile(1L);

        ResponseEntity<String> response = mobileController.deleteMobile(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deleteMobile_otherException_returnsBadRequest() {
        when(mobileService.getMobileById(1L)).thenReturn(Optional.of(sampleMobile));
        doThrow(new RuntimeException("Delete failed")).when(mobileService).deleteMobile(1L);

        ResponseEntity<String> response = mobileController.deleteMobile(1L);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Delete failed", response.getBody());
    }
}