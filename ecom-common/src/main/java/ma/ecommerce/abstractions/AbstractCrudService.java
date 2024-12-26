package ma.ecommerce.abstractions;

import ma.ecommerce.exceptions.EntityNotFoundInDBException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public abstract class AbstractCrudService<
        REPO extends AbstractCrudRepository<ENTITY, ID>,
        MAPPER extends AbstractMapper<ENTITY, DTO, ID>,
        ENTITY extends AbstractEntity<ID>,
        DTO,
        ID> {


    private final REPO repository;
    private final MAPPER mapper;

    public AbstractCrudService(REPO repository, MAPPER mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public DTO save(DTO dto) {
        ENTITY entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    public DTO update(ID id, DTO dto) {
        ENTITY entity = repository.findById(id).orElseThrow(()->new EntityNotFoundInDBException("ENTITY_NOT_FOUND"));
        mapper.refreshEntity(entity, dto);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    public DTO findById(ID id) {
        ENTITY entity = repository.findById(id).orElseThrow(()->new EntityNotFoundInDBException("ENTITY_NOT_FOUND"));
        return mapper.toDto(entity);
    }

    public void delete(ID id) {
        repository.deleteById(id);
    }

    public List<DTO> findByPagination(DTO dto, int page, int size) {
        return repository
                .findAll(Example.of(mapper.toEntity(dto)), PageRequest.of(page, size))
                .stream().map(mapper::toDto)
                .toList();
    }
}
