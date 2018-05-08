package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.ValueOptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ValueOption and its DTO ValueOptionDTO.
 */
@Mapper(componentModel = "spring", uses = {EUiMapper.class})
public interface ValueOptionMapper extends EntityMapper<ValueOptionDTO, ValueOption> {

    @Mapping(source = "eUi.id", target = "eUiId")
    ValueOptionDTO toDto(ValueOption valueOption);

    @Mapping(source = "eUiId", target = "eUi")
    ValueOption toEntity(ValueOptionDTO valueOptionDTO);

    default ValueOption fromId(Long id) {
        if (id == null) {
            return null;
        }
        ValueOption valueOption = new ValueOption();
        valueOption.setId(id);
        return valueOption;
    }
}
