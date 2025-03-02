package com.example.demoa4.repository;

import com.example.demoa4.domain.Entity;

import java.io.IOException;
import java.sql.SQLException;

public class MemoryRepository<T extends Entity> extends AbstractRepository<T> {

    @Override
    public void add(T elem) throws RepositoryException, IOException, SQLException {
        if (find(elem.getId()) != null) {
            throw new DuplicateIDException("Deja exista un obiect cu acest id.");
        }
        data.add(elem);
    }

    @Override
    public void delete(int id) throws RepositoryException, IOException, SQLException {
        T elem = find(id);
        if (elem == null) {
            throw new ObjectNotFoundException("Elementul nu a fost gasit.");
        }
        data.remove(elem);
    }

    @Override
    public T find(int id) throws SQLException {
        for (T elem : data){
            if (id == elem.getId()) {
                return elem;
            }
        }
        return null;
    }

    private int getPosition(T elem) {
        for (int i = 0; i < data.size(); i++) {
            if (elem.getId() == data.get(i).getId()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void update(int id, T elem) throws RepositoryException, IOException, SQLException {
        T elem2 = find(id);
        if (elem2 == null) {
            throw new ObjectNotFoundException("Elementul nu a fost gasit.");
        }
        int poz = getPosition(elem2);
        data.set(poz, elem);
    }

    @Override
    public int size(){
        return data.size();
    }
}
