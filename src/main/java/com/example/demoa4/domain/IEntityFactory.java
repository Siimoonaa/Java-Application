package com.example.demoa4.domain;

import java.text.ParseException;

public interface IEntityFactory<T extends Entity> {
    T createEntity(String line) throws ParseException;
}
