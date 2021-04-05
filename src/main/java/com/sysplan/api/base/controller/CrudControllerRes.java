package com.sysplan.api.base.controller;

import com.sysplan.api.base.controller.dto.ResourceAssemblerSupport;
import com.sysplan.api.base.service.CrudService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class CrudControllerRes<M, ID, Res, ResAssembler extends ResourceAssemblerSupport<Res, M>, S extends CrudService<M, ID>> {

    protected final S service;
    protected final ResAssembler resAssembler;

    @GetMapping
    @ApiOperation("list all values (limited to 300 registers)")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 204, message = "No Content"),
            }
    )
    public ResponseEntity<List<Res>> list() {
        var list = service.list();
        return list.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.ok(list.stream()
                        .map(resAssembler::toResource)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/paging/{page}/{limit}")
    @ApiOperation("list values by paging, page index from 0 until n, limiting count by page request.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 204, message = "No Content"),
            }
    )
    public ResponseEntity<List<Res>> paging(@ApiParam(value = "current page index starting in 0") @PathVariable int page,
                                            @ApiParam(value = "result limit count, whether 0 or less default value (100) will be used.") @PathVariable(required = false) Integer limit) {
        var list = service.paging(page, limit == null ? 0 : limit);
        return list.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.ok(list.stream()
                        .map(resAssembler::toResource)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("recover a value by id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 404, message = "Not Found"),
            }
    )
    public ResponseEntity<Res> get(@PathVariable ID id) {
        try {
            var model = service.getById(id);
            return model != null ?
                    ResponseEntity.ok(resAssembler.toResource(model)) :
                    ResponseEntity.notFound().build();
        } catch (Exception ex) {
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
    public ResponseEntity<Res> save(@Valid @RequestBody Res body) {
        try {
            M model = resAssembler.toDomain(body);
            model = service.save(model);
            return ResponseEntity.ok(resAssembler.toResource(model));
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    ex.getMessage(), ex);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("update a existing register")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 202, message = "Accepted"),
                    @ApiResponse(code = 400, message = "Bad Request")
            }
    )
    public void update(@Valid @RequestBody Res body) {
        try {
            M model = resAssembler.toDomain(body);
            service.update(model);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
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
    public void delete(@RequestBody Res body) {
        try {
            M model = resAssembler.toDomain(body);
            service.delete(model);
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
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
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

}
