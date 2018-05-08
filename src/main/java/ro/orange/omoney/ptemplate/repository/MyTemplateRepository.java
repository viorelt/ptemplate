package ro.orange.omoney.ptemplate.repository;

import ro.orange.omoney.ptemplate.domain.MyTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MyTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MyTemplateRepository extends JpaRepository<MyTemplate, Long> {

}
