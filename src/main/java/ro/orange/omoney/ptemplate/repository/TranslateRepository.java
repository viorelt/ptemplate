package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.Translate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Translate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TranslateRepository extends JpaRepository<Translate, Long> {

}
