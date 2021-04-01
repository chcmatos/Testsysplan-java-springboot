package com.sysplan.api.v1.controller;

import com.sysplan.api.base.controller.CrudControllerForUuid;
import com.sysplan.api.model.Client;
import com.sysplan.api.service.ClientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/client")
@ApiOperation(value = "/v1/client", tags = "Client Controller")
public final class ClientController extends CrudControllerForUuid<Client, ClientService> {

    public ClientController(ClientService service) {
        super(service);
    }
}
