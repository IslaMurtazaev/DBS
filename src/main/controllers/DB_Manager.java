package main.controllers;

import java.io.*;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import main.interfaces.Database;

public class DB_Manager implements Database {
    @Override
    public void save(Object object) {
        String className = getClassName(object);
        saveTo(className, object);
        System.out.println("Your "+ className +" object was successfully saved to "+ className +".json");
    }

    @Override
    public Object retrive(int id) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Object update(Object object) {
        return null;
    }

    private static String getClassName(Object object) {
        String[] reference = (object.getClass() +"").split("\\.");
        String className = reference[reference.length - 1];
        return className;
    }

    private static void saveTo(String name, Object objectToSave) {
        String filename = name +".json";

        String json = convertToJson(objectToSave);

        // Save JSON string to file
        try (
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)
        ) {
            out.println(json +",");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String convertToJson(Object objectToSave) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String json = "";
        try {
            json = mapper.writeValueAsString(objectToSave);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        return json;
    }

    private static String[] getObjects(String name) {
        String filename = name +".json";
        StringBuilder fileContent = new StringBuilder();

        try (
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            Scanner in = new Scanner(br);
        ) {
            while (in.hasNext()) {
                fileContent.append(in.nextLine()+"\n");
            }
        } catch (IOException ex) {
            return null;
        }

        return fileContent.toString().split(",\n");
    }
}
