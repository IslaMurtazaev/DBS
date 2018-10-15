package main.mocks;

import main.interfaces.Savable;

public class Pet extends Savable {
    private long personId;
    private String name;
    private String specie;

    public Pet() {}

    public Pet(long personId, String name, String specie) {
        this.personId = personId;
        this.name = name;
        this.specie = specie;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "personId=" + personId +
                ", name='" + name + '\'' +
                ", specie='" + specie + '\'' +
                '}';
    }
}
