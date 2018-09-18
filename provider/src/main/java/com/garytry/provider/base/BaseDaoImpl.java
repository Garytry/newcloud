package com.garytry.provider.base;

import java.util.List;

/**
 * @author gengdesehng
 * @date 2018/9/11
 */
public class BaseDaoImpl<T> implements BaseDao<T>{

    @Override
    public T findById(Object id) {
        return null;
    }

    @Override
    public List<T> list() {
        return null;
    }

    @Override
    public void save(T t) {

    }
}
