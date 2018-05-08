package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.SubmitActionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SubmitAction and its DTO SubmitActionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SubmitActionMapper extends EntityMapper<SubmitActionDTO, SubmitAction> {



    default SubmitAction fromId(Long id) {
        if (id == null) {
            return null;
        }
        SubmitAction submitAction = new SubmitAction();
        submitAction.setId(id);
        return submitAction;
    }
}
