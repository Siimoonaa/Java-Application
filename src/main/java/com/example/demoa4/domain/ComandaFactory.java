package com.example.demoa4.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ComandaFactory implements IEntityFactory{
    @Override
    public Comanda createEntity(String line) {
        String[] items = line.split(",");
        int id = Integer.parseInt(items[0].strip());
        ArrayList<Tort> tort = new ArrayList<>();
        String[] torturi = items[2].split("; ");
        for (String torts : torturi) {
            String id_tort = torts.split(":")[0].strip();
            String tip_tort = torts.split(":")[1].strip();
            tort.add(new Tort(Integer.parseInt(id_tort), tip_tort));
        }
        Date data;
        try {
            data = new SimpleDateFormat("yyyy-MM-dd").parse(items[1].strip());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new Comanda(id,data,tort);
    }
}
