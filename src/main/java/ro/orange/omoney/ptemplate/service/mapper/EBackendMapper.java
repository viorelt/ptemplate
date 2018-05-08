package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.EBackendDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EBackend and its DTO EBackendDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EBackendMapper extends EntityMapper<EBackendDTO, EBackend> {



    default EBackend fromId(Long id) {
        if (id == null) {
            return null;
        }
        EBackend eBackend = new EBackend();
        eBackend.setId(id);
        return eBackend;
    }
}
