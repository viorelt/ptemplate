package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.TemplateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Template and its DTO TemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {TUiMapper.class, TBackendMapper.class})
public interface TemplateMapper extends EntityMapper<TemplateDTO, Template> {

    @Mapping(source = "ui.id", target = "uiId")
    @Mapping(source = "backend.id", target = "backendId")
    @Mapping(source = "parent.id", target = "parentId")
    TemplateDTO toDto(Template template);

    @Mapping(source = "uiId", target = "ui")
    @Mapping(source = "backendId", target = "backend")
    @Mapping(target = "elements", ignore = true)
    @Mapping(source = "parentId", target = "parent")
    Template toEntity(TemplateDTO templateDTO);

    default Template fromId(Long id) {
        if (id == null) {
            return null;
        }
        Template template = new Template();
        template.setId(id);
        return template;
    }
}
