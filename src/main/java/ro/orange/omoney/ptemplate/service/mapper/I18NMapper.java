package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.I18NDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity I18N and its DTO I18NDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface I18NMapper extends EntityMapper<I18NDTO, I18N> {


    @Mapping(target = "languages", ignore = true)
    I18N toEntity(I18NDTO i18NDTO);

    default I18N fromId(Long id) {
        if (id == null) {
            return null;
        }
        I18N i18N = new I18N();
        i18N.setId(id);
        return i18N;
    }
}
