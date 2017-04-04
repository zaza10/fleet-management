
package za.co.rambau.fleet.fleet.management.data.controllers;

import java.util.List;

/**
 *Fleet management
 * @author fhatu
 * @param <T>
 */

public interface DataFacade<T> {

    int count();

    void create(T entity);

    void edit(T entity);

    T find(Object id);

    List<T> findAll();

    List<T> findRange(int from,int to);

    void remove(T entity);
    
}
