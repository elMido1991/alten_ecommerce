package ma.ecommerce.abstractions;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface AbstractMapper<ENTITY extends AbstractEntity<ID>, DTO, ID> {
    ENTITY toEntity(DTO dto);
    DTO toDto(ENTITY entity);
    void refreshEntity(@MappingTarget ENTITY entity,DTO dto);
    List<ENTITY> toEntities(List<DTO> dto);
    List<DTO> toDtos(List<ENTITY> entity);

}
