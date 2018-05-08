package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.TUi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TUi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TUiRepository extends JpaRepository<TUi, Long> {

}
