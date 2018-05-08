package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.LanguageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Language and its DTO LanguageDTO.
 */
@Mapper(componentModel = "spring", uses = {I18NMapper.class})
public interface LanguageMapper extends EntityMapper<LanguageDTO, Language> {

    @Mapping(source = "i18N.id", target = "i18NId")
    LanguageDTO toDto(Language language);

    @Mapping(source = "i18NId", target = "i18N")
    Language toEntity(LanguageDTO languageDTO);

    default Language fromId(Long id) {
        if (id == null) {
            return null;
        }
        Language language = new Language();
        language.setId(id);
        return language;
    }
}
