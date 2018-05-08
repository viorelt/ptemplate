package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.ElementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Element and its DTO ElementDTO.
 */
@Mapper(componentModel = "spring", uses = {TemplateMapper.class, EUiMapper.class, EBackendMapper.class, EValueMapper.class})
public interface ElementMapper extends EntityMapper<ElementDTO, Element> {

    @Mapping(source = "template.id", target = "templateId")
    @Mapping(source = "ui.id", target = "uiId")
    @Mapping(source = "backend.id", target = "backendId")
    @Mapping(source = "init.id", target = "initId")
    ElementDTO toDto(Element element);

    @Mapping(source = "templateId", target = "template")
    @Mapping(source = "uiId", target = "ui")
    @Mapping(source = "backendId", target = "backend")
    @Mapping(source = "initId", target = "init")
    Element toEntity(ElementDTO elementDTO);

    default Element fromId(Long id) {
        if (id == null) {
            return null;
        }
        Element element = new Element();
        element.setId(id);
        return element;
    }
}
