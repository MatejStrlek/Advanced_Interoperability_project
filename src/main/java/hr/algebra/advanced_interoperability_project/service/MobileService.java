package hr.algebra.advanced_interoperability_project.service;

import hr.algebra.advanced_interoperability_project.dto.MobileDTO;

import java.util.List;
import java.util.Optional;

public interface MobileService {
    List<MobileDTO> getAllMobiles();
    Optional<MobileDTO> getMobileById(Long id);
    void saveMobile(MobileDTO mobile);
    void updateMobile(Long id, MobileDTO mobile);
    void deleteMobile(Long id);
}