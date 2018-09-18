package com.garytry.provider.base;

import java.util.List;

/**
 * @author gengdesehng
 * @date 2018/9/11
 */
public interface BaseDao<T> {

    T findById(Object id);

    List<T> list();

    void save(T t);
}
