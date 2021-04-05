package com.sysplan.api.v2.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
public final class ClientDTO {

    @Getter
    @ApiModelProperty(value= "Client id")
    private final UUID id;

    @Getter
    @ApiModelProperty(value= "Client name")
    private final String name;

    @Getter
    @ApiModelProperty(value= "Client age")
    private final short age;

}
