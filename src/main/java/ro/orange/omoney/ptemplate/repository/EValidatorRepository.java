package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.EValidator;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EValidator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EValidatorRepository extends JpaRepository<EValidator, Long> {

}
