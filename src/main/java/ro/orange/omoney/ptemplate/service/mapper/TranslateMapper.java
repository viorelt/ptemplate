package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.TranslateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Translate and its DTO TranslateDTO.
 */
@Mapper(componentModel = "spring", uses = {I18NMapper.class})
public interface TranslateMapper extends EntityMapper<TranslateDTO, Translate> {

    @Mapping(source = "i18N.id", target = "i18NId")
    TranslateDTO toDto(Translate translate);

    @Mapping(source = "i18NId", target = "i18N")
    Translate toEntity(TranslateDTO translateDTO);

    default Translate fromId(Long id) {
        if (id == null) {
            return null;
        }
        Translate translate = new Translate();
        translate.setId(id);
        return translate;
    }
}
