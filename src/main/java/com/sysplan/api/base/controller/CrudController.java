package com.sysplan.api.base.controller;

import com.sysplan.api.base.controller.dto.ResourceAssemblerSupport;
import com.sysplan.api.base.service.CrudService;

public abstract class CrudController<M, ID, S extends CrudService<M, ID>>
        extends CrudControllerRes<M, ID, M, ResourceAssemblerSupport<M, M>, S> {

    private static class ResourceAssemblerSupportSimulated<M> implements ResourceAssemblerSupport<M, M> {

        @Override
        public M toResource(M model) {
            return model;
        }

        @Override
        public M toDomain(M model) {
            return model;
        }
    }

    public CrudController(S service) {
        super(service, new ResourceAssemblerSupportSimulated<>());
    }
}