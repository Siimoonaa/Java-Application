package com.example.demoa4.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Comanda extends Entity implements Serializable {

    Date data;
    List<Tort> tort;

    public Comanda(int id, Date data, List<Tort> tort) {
        super(id);
        this.data = data;
        this.tort = tort;
    }

    public java.sql.Date getData() {
        return new java.sql.Date (data.getTime());
    }

    public Collection<Tort> getTort() {
        return tort;
    }

    public String listaTorturi() {
        StringBuilder builder = new StringBuilder();
        for (Tort t : tort) {
            builder.append(t.getId()).append(":").append(t.getTip()).append("; ");
        }
        return builder.toString().trim();
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setTort(List<Tort> tort) {
        this.tort = tort;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Tort t : tort) {
            sb.append(t.getId()).append(":").append(t.getTip()).append("; ");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sd = sdf.format(data);
        return id + "," + sd + "," + sb;
    }
}
