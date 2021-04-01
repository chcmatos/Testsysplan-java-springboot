package com.sysplan.api.base.service;

import com.sysplan.api.base.model.ModelBase;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

/**
 * Service implement to CRUD using UUID as model id.
 * @param <M> target model type.
 * @param <R> target model repository type.
 * @hidden
 */
public abstract class CrudServiceImplUuid<M extends ModelBase<UUID>, R extends PagingAndSortingRepository<M, UUID>> extends CrudServiceImpl<M, UUID, R> {

    public CrudServiceImplUuid(R repository, Class<M> clazz) {
        super(repository, clazz);
    }

    @Override
    protected void onPrevSave(M model) {
        model.setId(UUID.randomUUID());
    }
}
