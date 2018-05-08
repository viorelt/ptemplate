package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.TBackendDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TBackend and its DTO TBackendDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TBackendMapper extends EntityMapper<TBackendDTO, TBackend> {



    default TBackend fromId(Long id) {
        if (id == null) {
            return null;
        }
        TBackend tBackend = new TBackend();
        tBackend.setId(id);
        return tBackend;
    }
}
