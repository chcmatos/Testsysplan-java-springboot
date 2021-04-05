package com.sysplan.api.v2.controller.dto.assembler;

import com.sysplan.api.base.controller.dto.ResourceAssemblerSupport;
import com.sysplan.api.model.Client;
import com.sysplan.api.v2.controller.dto.ClientDTO;
import org.springframework.stereotype.Component;

@Component
public final class ClientResourceAssembler implements ResourceAssemblerSupport<ClientDTO, Client> {

    @Override
    public ClientDTO toResource(Client model) {
        return ClientDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .age(model.getAge())
                .build();
    }

    @Override
    public Client toDomain(ClientDTO clientDTO) {
        Client c = new Client();
        c.setId(clientDTO.getId());
        c.setName(clientDTO.getName());
        c.setAge(clientDTO.getAge());
        return c;
    }

}
