package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.TBackend;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TBackend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TBackendRepository extends JpaRepository<TBackend, Long> {

}
