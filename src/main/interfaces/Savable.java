package main.interfaces;

public abstract class Savable {
    public long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String recieveFilename() {
        String[] reference = (this.getClass() + "").split("\\.");
        String className = reference[reference.length - 1];
        return className;
    }
}
