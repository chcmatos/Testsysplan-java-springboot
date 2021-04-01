package com.sysplan.api.v2.controller;

import com.sysplan.api.base.controller.CrudControllerResForUuid;
import com.sysplan.api.model.Client;
import com.sysplan.api.service.ClientService;
import com.sysplan.api.v2.controller.dto.ClientDTO;
import com.sysplan.api.v2.controller.dto.assembler.ClientResourceAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v2/client")
public final class ClientControllerV2 extends CrudControllerResForUuid<
        Client,
        ClientDTO,
        ClientResourceAssembler,
        ClientService> {

    public ClientControllerV2(ClientService service, ClientResourceAssembler assembler) {
        super(service, assembler);
    }
}
