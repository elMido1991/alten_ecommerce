package ma.ecommerce.abstractions;

import java.util.List;

public interface AbstractMapper<ENTITY extends AbstractEntity<ID>, DTO, ID> {
    ENTITY toEntity(DTO dto);
    DTO toDto(ENTITY entity);
    List<ENTITY> toEntities(List<DTO> dto);
    List<DTO> toDtos(List<ENTITY> entity);
}
