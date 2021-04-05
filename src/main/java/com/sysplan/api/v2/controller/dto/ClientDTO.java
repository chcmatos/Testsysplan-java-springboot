package com.sysplan.api.v2.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
public final class ClientDTO {

    @Getter
    @ApiModelProperty(value= "Client id")
    private final UUID id;

    @Getter
    @ApiModelProperty(value= "Client name")
    @NotNull
    private final String name;

    @Getter
    @ApiModelProperty(value= "Client age")
    @Min(18)
    @Max(199)
    private final short age;

}
