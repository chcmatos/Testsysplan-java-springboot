package com.sysplan.api.base.controller.dto;

public interface ResourceAssemblerSupport<Res, M> {

    Res toResource(M model);

    M toDomain(Res res);
}

