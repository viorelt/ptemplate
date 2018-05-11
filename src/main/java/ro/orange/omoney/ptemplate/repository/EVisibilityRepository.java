package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.EVisibility;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EVisibility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EVisibilityRepository extends JpaRepository<EVisibility, Long> {

}
