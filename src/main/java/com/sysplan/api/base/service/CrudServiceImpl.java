package com.sysplan.api.base.service;

import com.sysplan.api.base.model.ModelBase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Service implement to CRUD.
 * @param <M> target model
 * @param <ID> target model id type
 * @param <R> repository type
 * @hidden
 */
@AllArgsConstructor
public abstract class CrudServiceImpl<M extends ModelBase<ID>, ID, R extends PagingAndSortingRepository<M, ID>> implements CrudService<M, ID> {

    private final R repository;
    private final Class<M> clazz;

    protected final M requireNonNull(M model) {
        return Objects.requireNonNull(model, clazz.getSimpleName() + " can not be null!");
    }

    protected final ID requireNonNull(ID id) {
        return Objects.requireNonNull(id, "Invalid id!");
    }

    protected void onPrevSave(M model) { }

    @SuppressWarnings("unused")
    protected void onAfterSave(M model) { }

    @SuppressWarnings("unused")
    protected void onPrevUpdate(M model) { }

    @SuppressWarnings("unused")
    protected void onAfterUpdate(M model) { }

    @Override
    public List<M> list() {
        Pageable limit = PageRequest.of(0,FIND_ALL_LIMIT);
        var page = repository.findAll(limit);
        return page.toList();
    }

    @Override
    public List<M> paging(int page, int size) {
        Pageable limit = PageRequest.of(page, size);
        var res = repository.findAll(limit);
        return res.toList();
    }

    @Override
    public M getById(ID id) {
        return repository.findById(requireNonNull(id)).orElse(null);
    }

    @Override
    public M save(M model) {
        requireNonNull(model).setCreatedAt(LocalDateTime.now());
        onPrevSave(model);
        onAfterSave(model = repository.save(model));
        return model;
    }

    @Override
    public void update(M model) {
        requireNonNull(requireNonNull(model).getId());
        var curr = repository.findById(model.getId())
                .orElseThrow(() -> new RuntimeException(clazz.getSimpleName() + " (" + model.getId() + ") not found!"));
        onPrevUpdate(model);
        model.setCreatedAt(curr.getCreatedAt());
        model.setUpdatedAt(LocalDateTime.now());
        onAfterUpdate(repository.save(model));
    }

    @Override
    public void delete(M model) {
        repository.deleteById(requireNonNull(requireNonNull(model).getId()));
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(requireNonNull(id));
    }
}
