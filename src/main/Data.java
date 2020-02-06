package main;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.Stavka;

import java.io.*;
import java.util.ArrayList;

class Data {
    public static ArrayList<Stavka> readFromJson(String path) {
        if (!new File(path).exists()) {
            return new ArrayList<>();
        }

        try {
            JsonReader reader = new JsonReader(new FileReader(path));
            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<ArrayList<Stavka>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static boolean writeToJson(ArrayList<Stavka> list, String path) {
        try {
            Writer writer = new FileWriter(path);
            Gson gson = new Gson();
            gson.toJson(list, writer);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
