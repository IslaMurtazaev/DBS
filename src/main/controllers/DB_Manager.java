package main.controllers;

import java.util.List;

import main.interfaces.Database;
import main.interfaces.Savable;

import static main.controllers.HelperFunctions.saveToJsonFile;

public class DB_Manager implements Database {
    @Override
    public Object save(Savable object) {
        String className = object.recieveFilename();
        Object ormObject = saveToJsonFile(object, className);
        System.out.println("Object was successfully saved to "+ className +".json");

        return ormObject;
    }

    @Override
    public Object get(long id, Savable object) {
        Object ormObject =  HelperFunctions.retrieveJsonObject(id, object);
        System.out.println("Object was successfully retrieved!");

        return ormObject;
    }

    @Override
    public void delete(long id, Savable object) {
        List<String> updatedJson = HelperFunctions.deleteJsonObject(id, object);
        HelperFunctions.deleteFile(object);
        HelperFunctions.saveUpdatedJson(updatedJson,object);
        System.out.println("Object was successfully deleted!");
    }

    @Override
    public void update(long id, Savable object) {
        List<String> updatedJsonArray = HelperFunctions.updateJsonObject(id, object);
        HelperFunctions.deleteFile(object);
        HelperFunctions.saveUpdatedJson(updatedJsonArray, object);
        System.out.println("Object was successfully updated!");
    }
}
