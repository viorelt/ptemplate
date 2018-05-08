package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.EUiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EUi and its DTO EUiDTO.
 */
@Mapper(componentModel = "spring", uses = {I18NMapper.class})
public interface EUiMapper extends EntityMapper<EUiDTO, EUi> {

    @Mapping(source = "labelKey.id", target = "labelKeyId")
    @Mapping(source = "descriptionKey.id", target = "descriptionKeyId")
    @Mapping(source = "hintKey.id", target = "hintKeyId")
    EUiDTO toDto(EUi eUi);

    @Mapping(target = "options", ignore = true)
    @Mapping(source = "labelKeyId", target = "labelKey")
    @Mapping(source = "descriptionKeyId", target = "descriptionKey")
    @Mapping(source = "hintKeyId", target = "hintKey")
    EUi toEntity(EUiDTO eUiDTO);

    default EUi fromId(Long id) {
        if (id == null) {
            return null;
        }
        EUi eUi = new EUi();
        eUi.setId(id);
        return eUi;
    }
}
