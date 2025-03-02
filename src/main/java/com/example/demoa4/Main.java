package com.example.demoa4;//problema 2

import com.example.demoa4.domain.Comanda;
import com.example.demoa4.domain.ComandaFactory;
import com.example.demoa4.domain.Tort;
import com.example.demoa4.domain.TortFactory;
import com.example.demoa4.repository.*;
import com.example.demoa4.service.ServiceComanda;
import com.example.demoa4.service.ServiceTort;
//import com.example.demoa4.ui.UserInterface;
import com.example.demoa4.ui.UserInterface;
import com.example.demoa4.util.Settings;

import java.sql.SQLException;
import java.text.ParseException;

public class Main {

    public static MemoryRepository<Tort> getRepository() throws RepositoryException, ParseException, SQLException {

        Settings settings = Settings.getInstance("src/main/settings.properties");

        if ("file".equals(settings.getRepoType())) {
            return new FileRepository<Tort>(settings.getFileTort(), new TortFactory());
        } else if ("bin".equals(settings.getRepoType())) {
            return new BinaryFileRepository<Tort>(settings.getFileTort());
        } else if ("memory".equals(settings.getRepoType())) {
            return new MemoryRepository<Tort>();
        } else if ("sql".equals(settings.getRepoType())) {
            return new SQLTortRepository();
        }
        throw new IllegalArgumentException("Fisierul de setari e gresit.");
    }

    public static MemoryRepository<Comanda> getRepositoryC() throws RepositoryException, ParseException, SQLException {

        Settings settings = Settings.getInstance("src/main/settings.properties");

        if ("file".equals(settings.getRepoType())) {
            return new FileRepository<Comanda>(settings.getFileComanda(), new ComandaFactory());
        } else if ("bin".equals(settings.getRepoType())) {
            return new BinaryFileRepository<Comanda>(settings.getFileComanda());
        } else if ("memory".equals(settings.getRepoType())) {
            return new MemoryRepository<Comanda>();
        } else if ("sql".equals(settings.getRepoType())) {
            return new SQLComandaRepository();
        }
        throw new IllegalArgumentException("Fisierul de setari e gresit.");
    }

    public static void main(String[] args) throws RepositoryException, ParseException {
        HelloApplication.main(args);

//        try{
//            MemoryRepository<Tort> repository = getRepository();
//            ServiceTort serviceTort = new ServiceTort(repository);
//            MemoryRepository<Comanda> comandaRepository = getRepositoryC();
//            ServiceComanda serviceComanda = new ServiceComanda(comandaRepository);
//            UserInterface ui = new UserInterface(serviceTort, serviceComanda);
//            ui.run();
//        } catch (RepositoryException | SQLException e) {
//            System.out.println(e.getMessage());
//        }
    }
}
