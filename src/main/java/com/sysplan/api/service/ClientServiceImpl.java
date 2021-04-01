package com.sysplan.api.service;

import com.sysplan.api.model.Client;
import com.sysplan.api.repository.ClientRepository;
import com.sysplan.api.base.service.CrudServiceImplUuid;
import org.springframework.stereotype.Service;

@Service
final class ClientServiceImpl extends CrudServiceImplUuid<Client, ClientRepository> implements ClientService {

    public ClientServiceImpl(ClientRepository repository) {
        super(repository, Client.class);
    }

}