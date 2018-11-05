package main.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import main.interfaces.Savable;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HelperFunctions {
    static Object saveToJsonFile(Savable objectToSave, String name) {
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
        List<String> objects = getObjects(objectToSave.getClass());

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


    public static List<String> getObjects(Class aClass) {
        String className = getClassName(aClass);
        String filename = className + ".json";
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

        List<String> jsonObjects = splitJsonList(fileContent.toString());
        return jsonObjects;
    }


    private static List<String> splitJsonList(String text) {
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


    static void saveUpdatedJson(List<String> jsonObjects, Class aClass) {
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


    static List<String> updateJsonObject(long id, Savable object) {
        List<String> jsonObjects = getObjects(object.getClass());
        for (int i = 0; i < jsonObjects.size(); i++) {
            if (jsonObjects.get(i).substring(11, 12).equals(Long.toString(id))) {
                jsonObjects.set(i, convertToJson(object));
                break;
            }
        }

        return jsonObjects;
    }


    static void deleteFile(Class aClass) {
        String fileName = getClassName(aClass) + ".json";
        try {
            File file = new File(fileName);
            file.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    static Object retrieveJsonObject(long id, Class aClass) {
        List<String> jsonObjects = getObjects(aClass);

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


    static List deserializeList(List<String> jsonObjects, Class aClass) {
        List<Object> deserializedObjects = new ArrayList<>();

        for (String jsonObject : jsonObjects) {
            deserializedObjects.add(deserialize(jsonObject, aClass));
        }

        return deserializedObjects;
    }


    public static String getJsonById(long id, List<String> jsonObjects) {
        String target = "";

        for (String json : jsonObjects) {
            if (getJsonId(json) == id)
                target = json;
        }

        return target;
    }


    private static long getJsonId(String json) {
        Pattern p = Pattern.compile("\"id\" : -?\\d+");
        Matcher m = p.matcher(json);

        long id = 0;
        if (m.find()) {
            id = Long.valueOf(m.group().split(" : ")[1]);
        }

        return id;
    }


    static List<String> deleteJsonObject(long id, Class aClass){
        List<String> jsonObjects = HelperFunctions.getObjects(aClass);

        for (String json : jsonObjects) {
            if (getJsonId(json) == id) {
                jsonObjects.remove(json);
                break;
            }
        }

        return jsonObjects;
    }

    static String getClassName(Class aClass) {
        String[] reference = (aClass + "").split("\\.");
        String className = reference[reference.length - 1];
        return className;
    }
}