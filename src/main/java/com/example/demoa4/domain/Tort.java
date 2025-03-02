package com.example.demoa4.domain;

import java.io.Serializable;

public class Tort extends Entity implements Serializable {
    String tip;
    public Tort(int id, String tip){
        super(id);
        this.tip = tip;
    }


    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public String toString(){
        return id + "," + tip;
    }
}
