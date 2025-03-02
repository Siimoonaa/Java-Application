package com.example.demoa4.service;

import com.example.demoa4.domain.Comanda;
import com.example.demoa4.domain.Tort;
import com.example.demoa4.repository.EmptyListException;
import com.example.demoa4.repository.MemoryRepository;
import com.example.demoa4.repository.RepositoryException;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceComanda {
    MemoryRepository<Comanda> repoComanda;
    public ServiceComanda(MemoryRepository<Comanda> repoComanda) {
        this.repoComanda = repoComanda;
    }

    public void add(int id, Date data, List<Tort> tort) throws RepositoryException, IOException, SQLException {
        Comanda t = new Comanda(id, data, tort);
        if (tort.size() > 0){
            repoComanda.add(t);
        }
        else{
            throw new EmptyListException("Nu exista nici un tort in lista");
        }
    }

    public void remove(int id) throws RepositoryException, IOException, SQLException {
        repoComanda.delete(id);
    }

    public void update(int idv, Date data, List<Tort> tort) throws RepositoryException, IOException, SQLException {
        Comanda t = new Comanda(idv, data, tort);
        if (tort.size() > 0) {
            repoComanda.update(idv, t);
        }
        else {
            throw new EmptyListException("Nu exista nici un tort in lista");
        }
    }

    public Comanda print(int id) throws SQLException {
        return repoComanda.find(id);
    }

    public Collection<Comanda> getAll() throws SQLException {
        return repoComanda.getAll();
    }

    public void raportZilnic(Collection<Comanda> comenzi) {
        Map<String, Long> torturiPeZi = comenzi.stream()
                .collect(Collectors.groupingBy(
                        comanda -> {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(comanda.getData());
                            return cal.get(Calendar.YEAR) + "-" +
                                    (cal.get(Calendar.MONTH) + 1) + "-" +
                                    cal.get(Calendar.DAY_OF_MONTH);
                        },
                        Collectors.summingLong(comanda -> comanda.getTort().size())
                ));

        torturiPeZi.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> System.out.println("Data: " + entry.getKey() + ", Torturi comandate: " + entry.getValue()));
    }

    public void raportLunar(Collection<Comanda> comenzi) {
        Map<String, Long> torturiPeLuna = comenzi.stream()
                .filter(comanda -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(comanda.getData());
                    return cal.get(Calendar.YEAR) == 2024; // Filtrăm doar comenzile din 2024
                })
                .collect(Collectors.groupingBy(
                        comanda -> {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(comanda.getData());
                            return cal.get(Calendar.YEAR) + "-" +
                                    (cal.get(Calendar.MONTH) + 1); // Luna începe de la 0 în Calendar
                        },
                        Collectors.summingLong(comanda -> comanda.getTort().size())
                ));

        torturiPeLuna.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> System.out.println("Luna: " + entry.getKey() + ", Torturi comandate: " + entry.getValue()));
    }

    public void raportTorturiPopulare(Collection<Comanda> comenzi) {
        Map<String, Long> frecventaTorturi = comenzi.stream()
                .flatMap(comanda -> comanda.getTort().stream())
                .collect(Collectors.groupingBy(
                        tort -> tort.getTip(),
                        Collectors.counting()
                ));

        frecventaTorturi.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> System.out.println("Tort: " + entry.getKey() + ", Comenzi: " + entry.getValue()));
    }
}
