package ru.magenta.distance_calculator.db.models;

import java.util.Objects;

@SuppressWarnings("unused")
class DBEntity {
    private int id;

    public DBEntity() {
    }

    public DBEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBEntity dbEntity = (DBEntity) o;
        return Objects.equals(id, dbEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
