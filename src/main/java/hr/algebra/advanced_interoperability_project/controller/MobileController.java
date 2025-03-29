package hr.algebra.advanced_interoperability_project.controller;

import hr.algebra.advanced_interoperability_project.domain.Mobile;
import hr.algebra.advanced_interoperability_project.dto.MobileDTO;
import hr.algebra.advanced_interoperability_project.service.MobileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/rest/mobiles")
public class MobileController {
    private final MobileService mobileService;

    public MobileController(MobileService mobileService) {
        this.mobileService = mobileService;
    }

    @GetMapping
    public ResponseEntity<List<MobileDTO>> getAllMobiles() {
        return mobileService.getAllMobiles().isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(mobileService.getAllMobiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MobileDTO> getMobileById(@PathVariable Long id) {
        return mobileService.getMobileById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> saveMobile(@RequestBody @Valid MobileDTO mobileDTO) {
        try {
            mobileService.saveMobile(mobileDTO);
            return ResponseEntity.ok("Mobile has been saved");
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
            return ResponseEntity.ok("Mobile with id " + id + " has been updated");
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
