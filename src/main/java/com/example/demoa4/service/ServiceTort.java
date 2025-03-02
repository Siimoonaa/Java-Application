package com.example.demoa4.service;

import com.example.demoa4.domain.Tort;
import com.example.demoa4.repository.MemoryRepository;
import com.example.demoa4.repository.RepositoryException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public class ServiceTort {
    MemoryRepository<Tort> repoTort;

    public ServiceTort(MemoryRepository<Tort> repoTort) {
        this.repoTort = repoTort;
    }

    public void add(int id, String tip) throws RepositoryException, IOException, SQLException {
        Tort t = new Tort(id, tip);
        repoTort.add(t);
    }

    public void remove(int id) throws RepositoryException, IOException, SQLException {
        repoTort.delete(id);
    }

    public void update(int idv, String tip) throws RepositoryException, IOException, SQLException {
        Tort t = new Tort(idv, tip);
        repoTort.update(idv,t);
    }

    public Tort print(int id) throws SQLException {
         return repoTort.find(id);
    }

    public Collection<Tort> getAll() throws SQLException {
        return repoTort.getAll();
    }
}
