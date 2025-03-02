package com.example.demoa4.domain;

public class TortFactory implements IEntityFactory<Tort> {

    @Override
    public Tort createEntity(String line) {
        String[] items = line.split(",");
        int id = Integer.parseInt(items[0].strip());
        String tip = items[1].strip();
        return new Tort(id, tip);
    }
}
