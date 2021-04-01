package com.sysplan.api.base.controller;

import com.sysplan.api.base.model.ModelBaseUuid;
import com.sysplan.api.base.service.CrudService;

import java.util.UUID;

public abstract class CrudControllerForUuid<M extends ModelBaseUuid, S extends CrudService<M, UUID>> extends CrudController<M, UUID, S> {

    public CrudControllerForUuid(S service) {
        super(service);
    }
}