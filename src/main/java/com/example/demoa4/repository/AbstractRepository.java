package com.example.demoa4.repository;

import com.example.demoa4.domain.Entity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractRepository<T extends Entity> implements Iterable<T> {
    protected List<T> data = new ArrayList<T>();

    public abstract void add(T elem) throws RepositoryException, IOException, SQLException;
    public abstract void delete(int id) throws RepositoryException, IOException, SQLException;
    public abstract T find(int id) throws SQLException;
    public abstract void update(int id,T elem) throws RepositoryException, IOException, SQLException;
    public int size() {
        return data.size();
    }
    public Collection<T> getAll() throws SQLException {
        return new ArrayList<>(data);
    }
    public Iterator<T> iterator() {
        return data.iterator();
    }
}
