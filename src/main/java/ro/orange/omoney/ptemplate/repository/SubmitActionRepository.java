package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.SubmitAction;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SubmitAction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubmitActionRepository extends JpaRepository<SubmitAction, Long> {

}
