package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.EVisibilityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EVisibility and its DTO EVisibilityDTO.
 */
@Mapper(componentModel = "spring", uses = {EUiMapper.class, ElementMapper.class})
public interface EVisibilityMapper extends EntityMapper<EVisibilityDTO, EVisibility> {

    @Mapping(source = "eUi.id", target = "eUiId")
    @Mapping(source = "parent.id", target = "parentId")
    EVisibilityDTO toDto(EVisibility eVisibility);

    @Mapping(source = "eUiId", target = "eUi")
    @Mapping(source = "parentId", target = "parent")
    EVisibility toEntity(EVisibilityDTO eVisibilityDTO);

    default EVisibility fromId(Long id) {
        if (id == null) {
            return null;
        }
        EVisibility eVisibility = new EVisibility();
        eVisibility.setId(id);
        return eVisibility;
    }
}
