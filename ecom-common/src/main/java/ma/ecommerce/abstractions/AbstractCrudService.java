package ma.ecommerce.abstractions;

public class AbstractCrudService<
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
        ENTITY entity = repository.findById(id).orElseThrow();
        entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }
}
