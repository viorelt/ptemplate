package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.EValidatorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EValidator and its DTO EValidatorDTO.
 */
@Mapper(componentModel = "spring", uses = {EUiMapper.class, I18NMapper.class, EBackendMapper.class})
public interface EValidatorMapper extends EntityMapper<EValidatorDTO, EValidator> {

    @Mapping(source = "eUi.id", target = "eUiId")
    @Mapping(source = "errorMessage.id", target = "errorMessageId")
    @Mapping(source = "eBackend.id", target = "eBackendId")
    @Mapping(source = "eUi.id", target = "eUiId")
    @Mapping(source = "eBackend.id", target = "eBackendId")
    EValidatorDTO toDto(EValidator eValidator);

    @Mapping(source = "eUiId", target = "eUi")
    @Mapping(source = "errorMessageId", target = "errorMessage")
    @Mapping(source = "eBackendId", target = "eBackend")
    @Mapping(source = "eUiId", target = "eUi")
    @Mapping(source = "eBackendId", target = "eBackend")
    EValidator toEntity(EValidatorDTO eValidatorDTO);

    default EValidator fromId(Long id) {
        if (id == null) {
            return null;
        }
        EValidator eValidator = new EValidator();
        eValidator.setId(id);
        return eValidator;
    }
}
