package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.Language;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Language entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

}
