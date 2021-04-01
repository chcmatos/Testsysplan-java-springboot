package com.sysplan.api.base.service;

import java.util.List;

public interface CrudService<M, ID> {

    int FIND_ALL_LIMIT = 300;

    List<M> list();

    List<M> paging(int page, int size);

    M getById(ID id);

    M save(M model);

    void update(M model);

    void delete(M model);

    void deleteById(ID id);
}

