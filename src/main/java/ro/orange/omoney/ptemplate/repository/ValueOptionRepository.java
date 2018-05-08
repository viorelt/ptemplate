package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.ValueOption;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ValueOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValueOptionRepository extends JpaRepository<ValueOption, Long> {

}
