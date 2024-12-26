package ma.ecommerce.abstractions.reactive;

import ma.ecommerce.abstractions.AbstractEntity;
import ma.ecommerce.abstractions.AbstractMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractReactiveCrudService<
        REPO extends AbstractReactiveCrudRepository<ENTITY, ID>,
        MAPPER extends AbstractMapper<ENTITY, DTO, ID>,
        ENTITY extends AbstractEntity<ID>,
        DTO,
        ID> {


    private final REPO repository;
    private final MAPPER mapper;

    public AbstractReactiveCrudService(REPO repository, MAPPER mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Mono<DTO> save(DTO dto) {
        ENTITY entity = mapper.toEntity(dto);
        return repository.save(entity).map(mapper::toDto);
    }

    public Mono<DTO> update(ID id, DTO dto) {
        return repository.findById(id)
                .flatMap(entity -> {
                    mapper.refreshEntity(entity, dto);
                    return repository.save(entity).map(mapper::toDto);
                });
    }

    public void delete(ID id) {
        repository.deleteById(id);
    }

    public Mono<DTO> findById(ID id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public Flux<DTO> findByPagination(int page, int size) {
        return repository.findAll().map(mapper::toDto);
    }
}
