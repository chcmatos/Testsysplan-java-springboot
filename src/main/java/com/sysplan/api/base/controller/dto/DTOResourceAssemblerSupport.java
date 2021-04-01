package com.sysplan.api.base.controller.dto;

import com.sysplan.api.base.model.ModelBase;

public abstract class DTOResourceAssemblerSupport<Res, M extends ModelBase<?>> {

    public abstract Res toResource(M model);

    public abstract M toDomain(Res res);
}
