package com.sysplan.api.model;

import com.sysplan.api.base.model.ModelBaseUuid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Document
@EqualsAndHashCode(callSuper = true)
public class Client extends ModelBaseUuid {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Age is mandatory")
    @Min(1)
    @Max(199)
    private short age;

}
