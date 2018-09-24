package main.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HelperFunctions {


    public static void deleteFile(Object object) {
        String fileName = getClassName(object) + ".json";
        try {
            File file = new File(fileName);
            file.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static String getClassName(Object object) {
        String[] reference = (object.getClass() + "").split("\\.");
        String className = reference[reference.length - 1];
        return className;
    }


    public static void saveTo(String name, Object objectToSave) {
        String filename = name + ".json";
        String json = convertToJson(objectToSave);

        // Save JSON string to file
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


    public static String convertToJson(Object objectToSave) {
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

        }catch (IOException ex) {
            ex.printStackTrace();
        }

        List<String> jsonObjects = splitJson(fileContent.toString());
        return jsonObjects;
    }

    public static List<String> splitJson(String text){
        List<String> jsonObjects = new ArrayList();
        int open = 0;
        int close = 0;
        for(int i = 0; i<text.length();i++){
            if(text.charAt(i) == '{'){
                open = i;
            }
            if(text.charAt(i) == '}'){
                close = i;
                jsonObjects.add(text.substring(open, close+1));

            }
        }return jsonObjects;
    }


    public static List<String> updateJsonObject(long id, Object object) {
        String className = getClassName(object);
        List<String> jsonObjects = getObjects(className);
        for (int i = 0; i < jsonObjects.size(); i++) {
            if (jsonObjects.get(i).substring(11,12).equals(Long.toString(id))) {
                jsonObjects.set(i, convertToJson(object));
                break;
            }
        }

        return jsonObjects;
    }


    public static void saveUpdatedJson(List<String> jsonObjects, Object object){
        String filename = getClassName(object) + ".json";

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
    public static List<String> deleteJsonObject(long id,Object object){
        String className = HelperFunctions.getClassName(object);
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