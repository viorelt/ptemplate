package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.Element;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Element entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {

}
