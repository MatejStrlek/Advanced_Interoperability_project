package hr.algebra.advanced_interoperability_project.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class HackerDTO implements Serializable {
    private String maliciousCode;

    public HackerDTO(String maliciousCode) {
        this.maliciousCode = maliciousCode;
    }
}