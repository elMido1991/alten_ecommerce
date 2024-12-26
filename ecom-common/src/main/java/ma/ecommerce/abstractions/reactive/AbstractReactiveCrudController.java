package ma.ecommerce.abstractions.reactive;

import ma.ecommerce.abstractions.AbstractEntity;
import ma.ecommerce.abstractions.AbstractMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class AbstractReactiveCrudController<SERVICE extends AbstractReactiveCrudService<REPO, MAPPER, ENTITY, DTO, ID>,
        REPO extends AbstractReactiveCrudRepository<ENTITY, ID>,
        MAPPER extends AbstractMapper<ENTITY, DTO, ID>,
        ENTITY extends AbstractEntity<ID>,
        DTO,
        ID> {

    public final SERVICE service;

    public AbstractReactiveCrudController(SERVICE service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<DTO> save(@RequestBody DTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public Mono<DTO> update(@PathVariable("id") ID id, @RequestBody DTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") ID id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public Mono<DTO> findById(@PathVariable("id") ID id) {
        return service.findById(id);
    }

    @GetMapping
    public Flux<DTO> findByPagination(@RequestParam("page") int page, @RequestParam("size") int size) {
        return service.findByPagination(page, size);
    }

}

