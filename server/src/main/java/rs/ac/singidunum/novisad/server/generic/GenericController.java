package rs.ac.singidunum.novisad.server.generic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

public abstract  class GenericController <T,ID,D>{

    protected GenericService<T, ID> service;

    public GenericController(GenericService<T, ID> service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Iterable<D>> findAll() throws IllegalAccessException, InstantiationException {
        ArrayList<D> dtoList = new ArrayList<>();
        Iterable<T> original = service.findAll();
        for (T entity : original) {
            D dto = convertToDto(entity);
            dtoList.add(dto);
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<D> create(@RequestBody D dto) throws IllegalAccessException, InstantiationException {
        T entity = convertToEntity(dto);
        entity = service.save(entity);
        D savedDto = convertToDto(entity);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> findById(@PathVariable ID id) throws IllegalAccessException, InstantiationException {
        Optional<T> entityOptional = service.findById(id);
        if (entityOptional.isPresent()) {
            T entity = entityOptional.get();
            D dto = convertToDto(entity);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<D> update(@PathVariable ID id, @RequestBody D dto) throws IllegalAccessException, InstantiationException {
        Optional<T> entityOptional = service.findById(id);
        if (entityOptional.isPresent()) {
            T entity = convertToEntity(dto);
            entity = service.save(entity);
            D updatedDto = convertToDto(entity);
            return ResponseEntity.ok(updatedDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        Optional<T> entityOptional = service.findById(id);
        if (entityOptional.isPresent()) {
            T entity = entityOptional.get();
            service.delete(entity);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    protected abstract D convertToDto(T entity) throws IllegalAccessException, InstantiationException;

    protected abstract T convertToEntity(D dto) throws IllegalAccessException, InstantiationException;
}
