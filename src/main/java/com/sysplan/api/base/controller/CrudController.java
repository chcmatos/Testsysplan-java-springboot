package com.sysplan.api.base.controller;

import com.sysplan.api.base.model.ModelBase;
import com.sysplan.api.base.service.CrudService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
public abstract class CrudController<M extends ModelBase<ID>, ID, S extends CrudService<M, ID>> {

    protected final int PAGING_DEFAULT_LIMIT = 100;

    private final S service;

    @GetMapping
    @ApiOperation("list all values (limited to 300 registers)")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "No Content"),
            }
    )
    public ResponseEntity<List<M>> list() {
        var list = service.list();
        return list.isEmpty() ?
                ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .build() :
                ResponseEntity.ok(list);
    }

    @GetMapping("/paging/{page}/{limit}")
    @ApiOperation("list values by paging, page index from 0 until n, limiting count by page request.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 204, message = "No Content"),
            }
    )
    public ResponseEntity<List<M>> paging(@PathVariable int page, @PathVariable(required = false) Integer limit) {
        var list = service.paging(page, limit != null ? limit : PAGING_DEFAULT_LIMIT);
        return list.isEmpty() ?
                ResponseEntity
                        .status(HttpStatus.NO_CONTENT)
                        .build() :
                ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @ApiOperation("recover a value by id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 400, message = "Bad Request"),
            }
    )
    public ResponseEntity<M> get(@PathVariable ID id) {
        try {
            var model = service.getById(id);
            return model != null ?
                    ResponseEntity.ok(model) :
                    ResponseEntity.notFound().build();
        } catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ex.getMessage(), ex);
        }
    }

    @PostMapping
    @ApiOperation("insert a new register")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 400, message = "Bad Request"),
            }
    )
    public ResponseEntity<M> save(@RequestBody M model) {
        try {
            return ResponseEntity.ok(service.save(model));
        } catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ex.getMessage(), ex);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("update a existing register")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 202, message = "Accepted"),
                    @ApiResponse(code = 400, message = "Bad Request"),
            }
    )
    public void update(@RequestBody M model) {
        try {
            service.update(model);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ex.getMessage(), ex);
        }
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("remove target value")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 400, message = "Bad Request"),
            }
    )
    public void delete(@RequestBody M model) {
        try {
            service.delete(model);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("remove target value by id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 400, message = "Bad Request"),
            }
    )
    public void deleteById(@PathVariable ID id) {
        try {
            service.deleteById(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    ex.getMessage(), ex);
        }
    }
}
