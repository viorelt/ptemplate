package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.EUi;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EUi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EUiRepository extends JpaRepository<EUi, Long> {

}
