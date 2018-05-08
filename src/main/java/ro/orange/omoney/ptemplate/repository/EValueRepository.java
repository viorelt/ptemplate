package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.EValue;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EValueRepository extends JpaRepository<EValue, Long> {

}
