package main.controllers;

import java.util.List;

import main.interfaces.Database;
import main.interfaces.Savable;

import static main.controllers.HelperFunctions.saveToJsonFile;

public class DB_Manager implements Database {
    @Override
    public void save(Savable object) {
        String className = object.recieveFilename();
        saveToJsonFile(object, className);
        System.out.println("Object was successfully saved to "+ className +".json");
    }

    @Override
    public Object retrieve(long id, Savable object) {
        Object retrievedObject =  HelperFunctions.retrieveJsonObject(id, object);
        System.out.println("Object was successfully retrived!");
        return retrievedObject;
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
