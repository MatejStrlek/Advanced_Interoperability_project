package hr.algebra.advanced_interoperability_project.mapper;

import hr.algebra.advanced_interoperability_project.domain.Mobile;
import hr.algebra.advanced_interoperability_project.dto.MobileDTO;

public class MobileMapper {

    private MobileMapper() {
        // Private constructor to prevent instantiation
    }

    public static MobileDTO toDTO(Mobile mobile) {
        return new MobileDTO(mobile.getName(), mobile.getBrand(), mobile.getPrice(), mobile.getDescription(), mobile.getRating());
    }

    public static Mobile toEntity(MobileDTO mobileDTO) {
        return new Mobile(mobileDTO.getName(), mobileDTO.getBrand(), mobileDTO.getPrice(), mobileDTO.getDescription(), mobileDTO.getRating());
    }

    public static void updateEntity(MobileDTO mobileDTO, Mobile mobile) {
        mobile.setName(mobileDTO.getName());
        mobile.setBrand(mobileDTO.getBrand());
        mobile.setPrice(mobileDTO.getPrice());
        mobile.setDescription(mobileDTO.getDescription());
        mobile.setRating(mobileDTO.getRating());
    }
}
