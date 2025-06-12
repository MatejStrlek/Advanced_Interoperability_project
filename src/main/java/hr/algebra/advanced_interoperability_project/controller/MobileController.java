package hr.algebra.advanced_interoperability_project.controller;

import hr.algebra.advanced_interoperability_project.dto.MobileDTO;
import hr.algebra.advanced_interoperability_project.service.MobileService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/rest/mobiles")
public class MobileController {
    private final MobileService mobileService;
    private final JmsTemplate jmsTemplate;

    public MobileController(MobileService mobileService, JmsTemplate jmsTemplate) {
        this.mobileService = mobileService;
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping
    public ResponseEntity<List<MobileDTO>> getAllMobiles() {
        jmsTemplate.convertAndSend("Fetching all mobiles from the database: " + mobileService.getAllMobiles().size() + " mobiles");
        return mobileService.getAllMobiles().isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(mobileService.getAllMobiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MobileDTO> getMobileById(@PathVariable Long id) {
    jmsTemplate.convertAndSend("Fetching mobile with id: " + id);
        return mobileService.getMobileById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> saveMobile(@RequestBody @Valid MobileDTO mobileDTO) {
        try {
            mobileService.saveMobile(mobileDTO);

            jmsTemplate.convertAndSend("Saving the mobile to the database: " + mobileDTO.getName());

            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Mobile has been saved");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMobile(@PathVariable Long id, @RequestBody @Valid MobileDTO mobileDTO) {

        if (mobileService.getMobileById(id).isEmpty()) return ResponseEntity.notFound().build();

        try {
            mobileService.updateMobile(id, mobileDTO);
            jmsTemplate.convertAndSend("Updating mobile with id: " + id);
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Mobile with id " + id + " has been updated");
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMobile(@PathVariable Long id) {

        if (mobileService.getMobileById(id).isEmpty()) return ResponseEntity.notFound().build();

        try {
            mobileService.deleteMobile(id);
            jmsTemplate.convertAndSend("Deleting mobile with id: " + id);
            return ResponseEntity.ok("Mobile with id " + id + " has been deleted");
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}