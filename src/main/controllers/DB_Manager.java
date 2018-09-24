package main.controllers;

import java.util.List;

import main.interfaces.Database;
import static main.controllers.HelperFunctions.getClassName;
import static main.controllers.HelperFunctions.saveTo;

public class DB_Manager implements Database {
    @Override
    public void save(Object object) {
        String className = getClassName(object);
        saveTo(className, object);
        System.out.println("Your "+ className +" object was successfully saved to "+ className +".json");
    }

    @Override
    public Object retrive(long id) {
        return null;
    }

    @Override
    public void delete(long id,Object object) {
        List<String> updatedJson = HelperFunctions.deleteJsonObject(id,object);
        HelperFunctions.deleteFile(object);
        HelperFunctions.saveUpdatedJson(updatedJson,object);

    }

    @Override
    public void update(long id, Object object) {
        List<String> updatedJsonArray = HelperFunctions.updateJsonObject(id, object);
        HelperFunctions.deleteFile(object);
        HelperFunctions.saveUpdatedJson(updatedJsonArray, object);
        System.out.println("Object was successfully updated!");
    }
}
