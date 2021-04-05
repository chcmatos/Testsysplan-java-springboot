package com.sysplan.api.v2.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
public final class ClientDTO {

    @Getter
    @ApiModelProperty(value= "Client id")
    private final UUID id;

    @Getter
    @NotNull
    @NotEmpty
    @ApiModelProperty(value= "Client name")
    private final String name;

    @Getter
    @Min(18)
    @Max(199)
    @ApiModelProperty(value= "Client age")
    private final short age;

}
