package com.sysplan.api.base.controller;

import com.sysplan.api.base.controller.dto.DTOResourceAssemblerSupport;
import com.sysplan.api.base.model.ModelBaseUuid;
import com.sysplan.api.base.service.CrudService;

import java.util.UUID;

public abstract class CrudControllerResForUuid<M extends ModelBaseUuid,
        Res,
        ResAssembler extends DTOResourceAssemblerSupport<Res, M>,
        S extends CrudService<M, UUID>> extends CrudControllerRes<M, UUID, Res, ResAssembler, S> {

    public CrudControllerResForUuid(S service, ResAssembler resAssembler) {
        super(service, resAssembler);
    }
}