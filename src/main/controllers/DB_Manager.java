package main.controllers;

import java.util.List;

import main.interfaces.Database;
import main.interfaces.Savable;

import static main.controllers.HelperFunctions.getClassName;
import static main.controllers.HelperFunctions.saveToJsonFile;

public class DB_Manager implements Database {
    @Override
    public Object save(Savable object) {
        String className = getClassName(object.getClass());
        Object ormObject = saveToJsonFile(object, className);
        System.out.println("Object was successfully saved to "+ className +".json");

        return ormObject;
    }

    @Override
    public Object get(long id, Class aClass) {
        Object ormObject =  HelperFunctions.retrieveJsonObject(id, aClass);
        System.out.println("Object was successfully retrieved!");

        return ormObject;
    }

    @Override
    public void delete(long id, Class aClass) {
        List<String> updatedJson = HelperFunctions.deleteJsonObject(id, aClass);
        HelperFunctions.deleteFile(aClass);
        HelperFunctions.saveUpdatedJson(updatedJson, aClass);
        System.out.println("Object was successfully deleted!");
    }

    @Override
    public void update(long id, Savable object) {
        List<String> updatedJsonArray = HelperFunctions.updateJsonObject(id, object);
        HelperFunctions.deleteFile(object.getClass());
        HelperFunctions.saveUpdatedJson(updatedJsonArray, object.getClass());
        System.out.println("Object was successfully updated!");
    }
}
