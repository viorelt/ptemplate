package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.MyTemplateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MyTemplate and its DTO MyTemplateDTO.
 */
@Mapper(componentModel = "spring", uses = {TemplateMapper.class})
public interface MyTemplateMapper extends EntityMapper<MyTemplateDTO, MyTemplate> {

    @Mapping(source = "staticTemplate.id", target = "staticTemplateId")
    MyTemplateDTO toDto(MyTemplate myTemplate);

    @Mapping(target = "properties", ignore = true)
    @Mapping(source = "staticTemplateId", target = "staticTemplate")
    MyTemplate toEntity(MyTemplateDTO myTemplateDTO);

    default MyTemplate fromId(Long id) {
        if (id == null) {
            return null;
        }
        MyTemplate myTemplate = new MyTemplate();
        myTemplate.setId(id);
        return myTemplate;
    }
}
