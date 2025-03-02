package com.example.demoa4.repository;

import com.example.demoa4.domain.Entity;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileRepository<T extends Entity> extends MemoryRepository<T> {
    String fileName;

    public BinaryFileRepository(String fileName) throws RepositoryException {
        this.fileName = fileName;
        loadFile();
    }

    @Override
    public void add(T entity) throws RepositoryException, IOException, SQLException {
        super.add(entity);
        saveFile();
    }

    @Override
    public void delete(int id) throws RepositoryException, IOException, SQLException {
        super.delete(id);
        saveFile();
    }

    @Override
    public void update(int id, T entity) throws RepositoryException, IOException, SQLException {
        super.update(id, entity);
        saveFile();
    }

    protected void saveFile() throws RepositoryException {
        ObjectOutputStream out = null;
        try{
            out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(data);
            out.close();
        } catch (IOException e){
            System.out.println("Eroare la incarcarea fisierului:" + e.getMessage());
        }
    }

    protected void loadFile() throws RepositoryException {
        ObjectInputStream in = null;
        List<Entity> list = null;
        try{
            in = new ObjectInputStream(new FileInputStream(fileName));
            data = (ArrayList<T>) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Eroare la citirea din fisier:" + e.getMessage());
        }
    }
}
