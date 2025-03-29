package hr.algebra.advanced_interoperability_project.repository;

import hr.algebra.advanced_interoperability_project.domain.Mobile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MobileRepository extends JpaRepository<Mobile, Long> {
    List<Mobile> findMobileById (Long id);
}
