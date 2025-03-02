package com.example.demoa4.ui;

import com.example.demoa4.domain.Comanda;
import com.example.demoa4.domain.Tort;
import com.example.demoa4.repository.DuplicateIDException;
import com.example.demoa4.repository.EmptyListException;
import com.example.demoa4.repository.ObjectNotFoundException;
import com.example.demoa4.repository.RepositoryException;
import com.example.demoa4.service.ServiceComanda;
import com.example.demoa4.service.ServiceTort;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserInterface {
    ServiceTort serviceT;
    ServiceComanda serviceCom;

    public UserInterface(ServiceTort serviceT, ServiceComanda serviceCom) {
        this.serviceT = serviceT;
        this.serviceCom = serviceCom;
    }

    public void showMenu(){
        System.out.println("1. Creati un nou tort. \n2. Vizualizati un tort \n3. Actualizati un tort \n4. Stergeti un tort \n5. Vizualizati toate toruturile \n6. Vizualizati toate comenzile \n7. Creati o noua comanda \n8. Vizualizati o comanda \n9. Actualizati o comanda \n10. Stergeti o comanda \n11. Raport zilnic \n12. Raport lunar \n13. Torturi populare \n0. Exit" );
    }

    public void run() throws RepositoryException, ParseException, SQLException {
        boolean isRunning = true;
        while(isRunning){
            showMenu();
            Scanner scanner = new Scanner(System.in);
            System.out.println(">>>");
            int option = scanner.nextInt();
            switch(option){
                case 1:
                    addTort();
                    break;
                case 2:
                    showTort();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    removeTort();
                    break;
                case 5:
                    printAll(serviceT.getAll());
                    break;
                case 6:
                    printComenzi(serviceCom.getAll());
                    break;
                case 7:
                    addComanda();
                    break;
                case 8:
                    showComanda();
                    break;
                case 9:
                    updateComanda();
                    break;
                case 10:
                    removeComanda();
                    break;
                case 11:
                    zilnic();
                    break;
                case 12:
                    lunar();
                    break;
                case 13:
                    populare();
                    break;
                case 0:
                    isRunning = false;
            }
        }
    }

    private void populare() throws SQLException{
        serviceCom.raportTorturiPopulare(serviceCom.getAll());
    }

    private void lunar() throws SQLException {
        serviceCom.raportLunar(serviceCom.getAll());
    }

    private void zilnic() throws SQLException {
        serviceCom.raportZilnic(serviceCom.getAll());
    }

    private void printComenzi(Collection<Comanda> comenzi){
        for (Comanda m:comenzi){
            System.out.println(m);
        }
    }

    private void printAll(Collection<Tort> all){
        for (Tort m:all){
            System.out.println(m);
        }
    }

    private int readInt(Scanner scanner) {
        int intToRead;
        while (true) {
            try {
                intToRead = scanner.nextInt();
                break;
            } catch (InputMismatchException ex) {
                scanner.next();
                System.out.println("Te rog introdu un numar natural.");
            }
        }
        return intToRead;
    }

    private void addTort() throws RepositoryException {
        Scanner scanner = new Scanner(System.in);
        int id; String tip;
        System.out.println("Introduceti id-ul tortului: ");
        id  = readInt(scanner);
        System.out.println("Introduceti tipul tortului: ");
        tip = scanner.next();
        try {
            serviceT.add(id, tip);
        } catch (DuplicateIDException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addComanda() throws RepositoryException, ParseException, SQLException {
        Scanner scanner = new Scanner(System.in);
        int id; Date data; List<Tort> tort = new ArrayList<Tort>(); String d; int nr;
        System.out.println("Introduceti id-ul comenzii: ");
        id = readInt(scanner);
        System.out.println("Introduceti data comenzii: ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        d = scanner.next();
        data = dateFormat.parse(d);
        System.out.println("Cate torturi are comanda?");
        nr = readInt(scanner);
        for (int i=0;i<nr;i++) {
            System.out.println("Introduceti id-ul tortului pe care doriti sa il adaugati: ");
           int  idt = readInt(scanner);
            Tort t = serviceT.print(idt);
            tort.add(t);
        }
        try {
            serviceCom.add(id, data, tort);
        } catch (DuplicateIDException | IOException e){
            System.out.println(e.getMessage());
        } catch (EmptyListException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void removeTort() throws RepositoryException {
        Scanner scanner = new Scanner(System.in);
        int id;
        System.out.println("Introduceti id-ul tortului: ");
        id  = readInt(scanner);
        try {
            serviceT.remove(id);
        } catch (ObjectNotFoundException e){
            System.out.println(e.getMessage());
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void removeComanda() throws RepositoryException {
        Scanner scanner = new Scanner(System.in);
        int id;
        System.out.println("Introduceti id-ul comenzii: ");
        id = readInt(scanner);
        try{
            serviceCom.remove(id);
        } catch (ObjectNotFoundException | IOException e){
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void update() throws RepositoryException{
        Scanner scanner = new Scanner(System.in);
        int id;
        System.out.println("Introduceti id-ul tortului pe care vreti sa il modificati: ");
        id  = readInt(scanner);
        String tip;
        System.out.println("Introduceti tipul tortului: ");
        tip = scanner.next();
        try {
            serviceT.update(id, tip);
        } catch(ObjectNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void updateComanda() throws RepositoryException, ParseException, SQLException {
        Scanner scanner = new Scanner(System.in);
        int id;
        System.out.println("Introduceti id-ul comenzii pe care vreti sa o modificati: ");
        id = readInt(scanner);
        List<Tort> tort = new ArrayList<Tort>(); String d; Date data; int nr;
        System.out.println("Introduceti noua data a comenzii: ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        d = scanner.next();
        data = dateFormat.parse(d);
        System.out.println("Cate torturi are comanda acum?");
        nr = readInt(scanner);
        for (int i=0;i<nr;i++) {
            System.out.println("Introduceti id-ul tortului pe care doriti sa il adaugati acum la comanda: ");
            int  idt = readInt(scanner);
            Tort t = serviceT.print(idt);
            tort.add(t);
        }
        try{
            serviceCom.update(id, data, tort);
        } catch (ObjectNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        } catch (EmptyListException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showTort() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int id;
        System.out.println("Introduceti id-ul tortului: ");
        id  = readInt(scanner);
        Tort t = serviceT.print(id);
        System.out.println(t);
    }

    private void showComanda() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int id;
        System.out.println("Introduceti id-ul comenzii: ");
        id  = readInt(scanner);
        Comanda t = serviceCom.print(id);
        System.out.println(t);
    }
}
