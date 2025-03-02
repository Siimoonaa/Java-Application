package com.example.demoa4.repository;

import com.example.demoa4.domain.Tort;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SQLTortRepository extends MemoryRepository<Tort> {
    private Connection connection;
    private String DB_URL = "jdbc:sqlite:tort_db";
    public SQLTortRepository() throws SQLException, RepositoryException {
        openConnection();
        createTable();
        loadData();
    }
    private void loadData() throws SQLException {
        Collection<Tort> torts = getAll();
        data.addAll(torts);
    }

    private void openConnection() throws SQLException, RepositoryException {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(DB_URL);
        try {
            if (connection == null || connection.isClosed()){
                connection = ds.getConnection();
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public void closeConnection(){
        if (connection != null)
            try{
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }

    private void createTable(){
        String createSQL = "CREATE TABLE IF NOT EXISTS tort (" +
                "id int, tip varchar(100)," +
                "PRIMARY KEY (id))";
        try{
            Statement createStatement = connection.createStatement();
            createStatement.execute(createSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Tort tort) throws RepositoryException, IOException, SQLException {
        super.add(tort);
        String insertSQL = "INSERT INTO tort VALUES (?,?);";
        try(PreparedStatement insertStatement = connection.prepareStatement(insertSQL)){
            insertStatement.setInt(1,tort.getId());
            insertStatement.setString(2, tort.getTip());
            int numberOfRowsAffected = insertStatement.executeUpdate();
            System.out.println("Nr de linii afectate in adaugare:" + numberOfRowsAffected);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) throws RepositoryException, IOException, SQLException {
        super.delete(id);
        String deleteSQL = "DELETE FROM tort WHERE id = (?);";
        try(PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)){
            deleteStatement.setInt(1,id);
            int numberOfRowsAffected = deleteStatement.executeUpdate();
            System.out.println("Nr de linii afectate in stergere:" + numberOfRowsAffected);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void update(int id, Tort tort) throws RepositoryException, IOException, SQLException {
        super.update(id,tort);
        String updateSQL = "UPDATE tort SET tip = (?) WHERE id = (?);";
        try(PreparedStatement updateStatement = connection.prepareStatement(updateSQL)){
            updateStatement.setString(1,tort.getTip());
            updateStatement.setInt(2,id);
            int numberOfRowsAffected = updateStatement.executeUpdate();
            System.out.println("Nr de linii afectate in update:" + numberOfRowsAffected);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Collection<Tort> getAll() throws SQLException {
        super.getAll();
        List<Tort> resultList = new ArrayList<>();
        String selectSQL = "SELECT * FROM tort;";
        try(PreparedStatement getAllStatement = connection.prepareStatement(selectSQL)){
            ResultSet result = getAllStatement.executeQuery();
            while (result.next()){
                int id = result.getInt("id");
                String tip = result.getString("tip");
                Tort t = new Tort(id,tip);
                resultList.add(t);
            }
            return resultList;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Tort find(int id) throws SQLException {
        super.find(id);
        String findSQL = "SELECT * FROM tort WHERE id = (?);";
        try(PreparedStatement findStatement = connection.prepareStatement(findSQL)){
            findStatement.setInt(1,id);
            ResultSet result = findStatement.executeQuery();
            if (result.next()){
                String tip = result.getString("tip");
                return new Tort(id,tip);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }
}
