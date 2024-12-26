package ma.ecommerce.abstractions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractCrudController<SERVICE extends AbstractCrudService<REPO, MAPPER, ENTITY, DTO, ID>,
        REPO extends AbstractCrudRepository<ENTITY, ID>,
        MAPPER extends AbstractMapper<ENTITY, DTO, ID>,
        ENTITY extends AbstractEntity<ID>,
        DTO,
        ID> {

    public final SERVICE service;

    public AbstractCrudController(SERVICE service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DTO save(@RequestBody DTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public DTO update(@PathVariable("id") ID id, @RequestBody DTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") ID id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public DTO findById(@PathVariable("id") ID id) {
        return service.findById(id);
    }

    @GetMapping
    public List<DTO> findByPagination(@RequestBody DTO dto, @RequestParam("page") int page, @RequestParam("size") int size) {
        return service.findByPagination(dto, page, size);
    }

}
