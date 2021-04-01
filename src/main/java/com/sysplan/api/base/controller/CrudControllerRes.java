package com.sysplan.api.base.controller;

import com.sysplan.api.base.controller.dto.DTOResourceAssemblerSupport;
import com.sysplan.api.base.model.ModelBase;
import com.sysplan.api.base.service.CrudService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class CrudControllerRes<M extends ModelBase<ID>, ID, Res, ResAssembler extends DTOResourceAssemblerSupport<Res, M>, S extends CrudService<M, ID>> {

    protected final int PAGING_DEFAULT_LIMIT = 100;

    private final S service;
    private final ResAssembler resAssembler;

    @GetMapping
    @ApiOperation("list all clients (limited to 300 registers)")
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
    @ApiOperation("list clients by paging, page index from 0 until n, limiting by page request.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Ok"),
                    @ApiResponse(code = 204, message = "No Content"),
            }
    )
    public ResponseEntity<List<Res>> paging(@PathVariable int page, @PathVariable(required = false) Integer limit) {
        var list = service.paging(page, limit != null ? limit : PAGING_DEFAULT_LIMIT);
        return list.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.ok(list.stream()
                        .map(resAssembler::toResource)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("recover a client by id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 400, message = "Bad Request"),
            }
    )
    public Res get(@PathVariable ID id) {
        return resAssembler.toResource(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("insert a new client")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Created"),
                    @ApiResponse(code = 400, message = "Bad Request"),
            }
    )
    public M save(@RequestBody Res body) {
        M model = resAssembler.toDomain(body);
        return service.save(model);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("update a existing client")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 202, message = "Accepted"),
                    @ApiResponse(code = 400, message = "Bad Request"),
            }
    )
    public void update(@RequestBody Res body) {
        M model = resAssembler.toDomain(body);
        service.update(model);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("remove target client")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 400, message = "Bad Request"),
            }
    )
    public void delete(@RequestBody Res body) {
        M model = resAssembler.toDomain(body);
        service.delete(model);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("remove target client by id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 400, message = "Bad Request"),
            }
    )
    public void deleteById(@PathVariable ID id) {
        service.deleteById(id);
    }

}
