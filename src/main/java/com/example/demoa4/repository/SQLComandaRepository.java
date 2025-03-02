package com.example.demoa4.repository;

import com.example.demoa4.domain.Comanda;
import com.example.demoa4.domain.Tort;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SQLComandaRepository extends MemoryRepository<Comanda> {
    private Connection connection;
    private String DB_URL = "jdbc:sqlite:comanda_db";
    public SQLComandaRepository() throws RepositoryException, SQLException {
        openConnection();
        createTable();
        loadData();
    }
    private void loadData() throws SQLException {
        Collection<Comanda> comands = getAll();
        data.addAll(comands);
    }

    private void openConnection() throws RepositoryException {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(DB_URL);
        try{
            if (connection == null || connection.isClosed()){
                connection = ds.getConnection();
            }
        }catch (SQLException e){
            throw new RepositoryException(e.getMessage());
        }
    }

    public void closeConnection() {
        if (connection != null)
            try{
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }

    private void createTable(){
        String createSQL = "CREATE TABLE IF NOT EXISTS comanda(" +
                "id int, data date, lista_torturi varchar(1000)," +
                "PRIMARY KEY (id))";
        try{
            Statement createStatement = connection.createStatement();
            createStatement.execute(createSQL);;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String listaToString(List<Tort> tort){
        StringBuilder sb = new StringBuilder();
        for (Tort t : tort) {
            sb.append(t.getId()).append(":").append(t.getTip()).append("; ");
        }
        return sb + "";
    }

    public void add(Comanda comanda) throws RepositoryException, IOException, SQLException {
        super.add(comanda);
        String insertSQL = "INSERT INTO comanda VALUES (?,?,?);";
        try(PreparedStatement insertStatement = connection.prepareStatement(insertSQL)){
            insertStatement.setInt(1,comanda.getId());
            insertStatement.setDate(2,comanda.getData());
            String torturi = comanda.listaTorturi();
            insertStatement.setString(3,torturi);
            int numberOfRowsAffected = insertStatement.executeUpdate();
            System.out.println("Nr de linii afectate in adaugare:" + numberOfRowsAffected);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) throws RepositoryException, IOException, SQLException {
        super.delete(id);
        String deleteSQL = "DELETE FROM comanda WHERE id = (?);";
        try(PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)){
            deleteStatement.setInt(1,id);
            int numberOfRowsAffected = deleteStatement.executeUpdate();
            System.out.println("Nr de linii afectate in stergere:" + numberOfRowsAffected);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void update(int id, Comanda comanda) throws RepositoryException, IOException, SQLException {
        super.update(id,comanda);
        String updateSQL = "UPDATE comanda SET data = (?), lista_torturi = (?) WHERE id = (?);";
        try(PreparedStatement updateStatement = connection.prepareStatement(updateSQL)){
            updateStatement.setDate(1,comanda.getData());
            String torturi = comanda.listaTorturi();
            updateStatement.setString(2,torturi);
            updateStatement.setInt(3,id);
            int numberOfRowsAffected = updateStatement.executeUpdate();
            System.out.println("Nr de linii afectate in update:" + numberOfRowsAffected);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Collection<Comanda> getAll() throws SQLException {
        super.getAll();
        List<Comanda> resultList = new ArrayList<>();
        String selectSQL = "SELECT * FROM comanda;";
        try (PreparedStatement getAllStatement = connection.prepareStatement(selectSQL)) {
            ResultSet result = getAllStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                Date data = result.getDate("data");
                String lista_torturi = result.getString("lista_torturi");
                List<Tort> torturi = new ArrayList<>();
                String[] split = lista_torturi.split(";");
                for (String s : split) {
                    String[] split2 = s.split(":");
                    int id_t = Integer.parseInt(split2[0].trim());
                    String type_t = split2[1].trim();
                    torturi.add(new Tort(id_t, type_t));
                }
                resultList.add(new Comanda(id, data, torturi));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    public Comanda find(int id) throws SQLException {
        super.find(id);
        String findSQL = "SELECT * FROM comanda WHERE id = (?);";
        try (PreparedStatement findStatement = connection.prepareStatement(findSQL)) {
            findStatement.setInt(1, id);
            ResultSet result = findStatement.executeQuery();
            if (result.next()) {
                Date data = result.getDate("data");
                String lista_torturi = result.getString("lista_torturi");
                List<Tort> torturi = new ArrayList<>();
                String[] split = lista_torturi.split(";");
                for (String s : split) {
                    String[] split2 = s.split(":");
                    int id_t = Integer.parseInt(split2[0].trim());
                    String type_t = split2[1].trim();
                    torturi.add(new Tort(id_t, type_t));
                }
                return new Comanda(id, data, torturi);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
