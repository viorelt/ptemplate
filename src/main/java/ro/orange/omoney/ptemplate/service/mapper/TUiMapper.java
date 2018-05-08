package ro.orange.omoney.ptemplate.service.mapper;

import ro.orange.omoney.ptemplate.domain.*;
import ro.orange.omoney.ptemplate.service.dto.TUiDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TUi and its DTO TUiDTO.
 */
@Mapper(componentModel = "spring", uses = {SubmitActionMapper.class})
public interface TUiMapper extends EntityMapper<TUiDTO, TUi> {

    @Mapping(source = "post.id", target = "postId")
    TUiDTO toDto(TUi tUi);

    @Mapping(source = "postId", target = "post")
    TUi toEntity(TUiDTO tUiDTO);

    default TUi fromId(Long id) {
        if (id == null) {
            return null;
        }
        TUi tUi = new TUi();
        tUi.setId(id);
        return tUi;
    }
}
