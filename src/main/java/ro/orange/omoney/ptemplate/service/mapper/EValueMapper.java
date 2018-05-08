package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.EValueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EValue and its DTO EValueDTO.
 */
@Mapper(componentModel = "spring", uses = {MyTemplateMapper.class, ElementMapper.class})
public interface EValueMapper extends EntityMapper<EValueDTO, EValue> {

    @Mapping(source = "myTemplate.id", target = "myTemplateId")
    @Mapping(source = "element.id", target = "elementId")
    EValueDTO toDto(EValue eValue);

    @Mapping(source = "myTemplateId", target = "myTemplate")
    @Mapping(source = "elementId", target = "element")
    EValue toEntity(EValueDTO eValueDTO);

    default EValue fromId(Long id) {
        if (id == null) {
            return null;
        }
        EValue eValue = new EValue();
        eValue.setId(id);
        return eValue;
    }
}
