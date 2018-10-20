package main.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import main.interfaces.Savable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class HelperFunctions {
    public static Object saveToJsonFile(Savable objectToSave, String name) {
        generateId(objectToSave, name);

        String filename = name + ".json";
        String json = convertToJson(objectToSave);

        try (
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)
        ) {
            out.println(json + ",");
            return objectToSave;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }


    private static void generateId(Savable objectToSave, String name) {
        List<String> objects = getObjects(name);

        long id;
        if (objects.isEmpty()) {
            id = 1;
        } else {
            String lastObject = objects.get(objects.size() - 1);
            Savable savable = (Savable) deserialize(lastObject, objectToSave.getClass());
            id = savable.getId() + 1;
        }

        objectToSave.setId(id);
    }


    private static String convertToJson(Savable objectToSave) {
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


    public static List<String> getObjects(String name) {
        String filename = name + ".json";
        StringBuilder fileContent = new StringBuilder();

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            Scanner in = new Scanner(br);

            while (in.hasNext()) {
                fileContent.append(in.nextLine() + "\n");
            }

        } catch (IOException ex) {
            return new ArrayList<>();
        }

        List<String> jsonObjects = splitJson(fileContent.toString());
        return jsonObjects;
    }


    private static List<String> splitJson(String text) {
        List<String> jsonObjects = new ArrayList();
        int open = 0;
        int close = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '{') {
                open = i;
            }
            if (text.charAt(i) == '}') {
                close = i;
                jsonObjects.add(text.substring(open, close + 1));
            }
        }
        return jsonObjects;
    }


    public static void saveUpdatedJson(List<String> jsonObjects, Class aClass) {
        String filename = getClassName(aClass) + ".json";

        try (
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)
        ) {
            for (String jsonObject : jsonObjects) {
                    out.println(jsonObject + ",");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static List<String> updateJsonObject(long id, Savable object) {
        String className = object.recieveFilename();
        List<String> jsonObjects = getObjects(className);
        for (int i = 0; i < jsonObjects.size(); i++) {
            if (jsonObjects.get(i).substring(11, 12).equals(Long.toString(id))) {
                jsonObjects.set(i, convertToJson(object));
                break;
            }
        }

        return jsonObjects;
    }


    public static void deleteFile(Class aClass) {
        String fileName = getClassName(aClass) + ".json";
        try {
            File file = new File(fileName);
            file.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static Object retrieveJsonObject(long id, Class aClass) {
        String className = getClassName(aClass);
        List<String> jsonObjects = getObjects(className);

        String target = getJsonById(id, jsonObjects);

        Object jsonObject = deserialize(target, aClass);
        return jsonObject;
    }


    public static Object deserialize(String json, Class aClass) {
        ObjectMapper mapper = new ObjectMapper();

        Object deserializedObject = null;

        try {
            deserializedObject = mapper.readValue(json, aClass);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return deserializedObject;
    }


    public static String getJsonById(long id, List<String> jsonObjects) {
        String target = "";
        for (int i = 0; i < jsonObjects.size(); i++) {
            if (jsonObjects.get(i).substring(11, 12).equals(Long.toString(id))) {
                target = jsonObjects.get(i);
            }
        }
        return target;
    }


    public static List<String> deleteJsonObject(long id, Class aClass){
        String className = getClassName(aClass);
        List<String> jsonObjects = HelperFunctions.getObjects(className);

        for (int i = 0; i < jsonObjects.size(); i++) {
            if (jsonObjects.get(i).substring(11,12).equals(Long.toString(id))) {
                jsonObjects.remove(i);
                break;
            }
        }
        return jsonObjects;
    }

    public static String getClassName(Class aClass) {
        String[] reference = (aClass + "").split("\\.");
        String className = reference[reference.length - 1];
        return className;
    }
}