package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.EBackend;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EBackend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EBackendRepository extends JpaRepository<EBackend, Long> {

}
