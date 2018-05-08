package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.I18N;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the I18N entity.
 */
@SuppressWarnings("unused")
@Repository
public interface I18NRepository extends JpaRepository<I18N, Long> {

}
