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
    public static void saveTo(String name, Savable objectToSave) {
        String filename = name + ".json";
        String json = convertToJson(objectToSave);

        try (
                FileWriter fw = new FileWriter(filename, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)
        ) {
            out.println(json + ",");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static String convertToJson(Savable objectToSave) {
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
            ex.printStackTrace();
        }

        List<String> jsonObjects = splitJson(fileContent.toString());
        return jsonObjects;
    }

    public static List<String> splitJson(String text) {
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


    public static void saveUpdatedJson(List<String> jsonObjects, Savable object) {
        String filename = object.recieveFilename() + ".json";

        for (int i = 0; i < jsonObjects.size(); i++) {
            // Create new JSON file
            try (
                    FileWriter fw = new FileWriter(filename, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)
            ) {
                out.println(jsonObjects.get(i) + ",");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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

    public static void deleteFile(Savable object) {
        String fileName = object.recieveFilename() + ".json";
        try {
            File file = new File(fileName);
            file.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Object retrieveJsonObject(long id, Savable object) {
        ObjectMapper mapper = new ObjectMapper();
        String className = object.recieveFilename();
        List<String> jsonObjects = getObjects(className);
        String target = getJsonById(id, jsonObjects);
        try {
            return mapper.readValue(target, object.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObjects;
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

    public static List<String> deleteJsonObject(long id, Savable object){
        String className = object.recieveFilename();
        List<String> jsonObjects = HelperFunctions.getObjects(className);

        for (int i = 0; i < jsonObjects.size(); i++) {
            if (jsonObjects.get(i).substring(11,12).equals(Long.toString(id))) {
                jsonObjects.remove(i);
                break;
            }
        }
        return jsonObjects;
    }

}