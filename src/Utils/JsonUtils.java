package Utils;

import Database.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonUtils {
    public static Connection[] getConnections(String fileName){
        String f = new File("").getAbsolutePath();
        try {
            JsonReader file = new JsonReader(new FileReader(f.concat("/src/assets/Rooms/" + fileName + ".json")));
            Gson gson = new Gson();
            Connection[] connections = gson.fromJson(file, Connection[].class);
            return connections;
        } catch (FileNotFoundException e) {
            Output.print("Couldn't find \"" + f + "\". Exiting.", "red");
            System.exit(0);
        }
        return null;
    }
    public static Room[] getRooms(String fileName){
        String f = new File("").getAbsolutePath();
        try {
            JsonReader file = new JsonReader(new FileReader(f.concat("/src/Assets/Rooms/" + fileName + ".json")));
            Gson gson = new Gson();
            Room[] rooms = gson.fromJson(file, Room[].class);
            return rooms;
        } catch (FileNotFoundException e) {
            Output.print("Couldn't find \"" + f + "\". Exiting.", "red");
            System.exit(0);
        }
        return null;
    }
    public static Coordinate[] getCoordinates(String fileName){
        String f = new File("").getAbsolutePath();
        try{
            JsonReader file = new JsonReader(new FileReader(f.concat("/src/Assets/Trees/" + fileName + ".json")));
            Gson gson = new Gson();
            Coordinate[] coordinates = gson.fromJson(file, Coordinate[].class);
            return coordinates;
        } catch (FileNotFoundException e) {
            Output.print("Couldn't find \"" + f + "\". Exiting.", "red");
            System.exit(0);
        }
        return null;
    }
    public static StoreObject[] getStoreObjects(String fileName){
        String f = new File("").getAbsolutePath();
        try{
            JsonReader file = new JsonReader(new FileReader(f.concat("/src/Assets/Trees/" + fileName + ".json")));
            Gson gson = new Gson();
            StoreObject[] storeObjects = gson.fromJson(file, StoreObject[].class);
            return storeObjects;
        } catch (FileNotFoundException e) {
            Output.print("Couldn't find \"" + f + "\". Exiting.", "red");
            System.exit(0);
        }
        return null;
    }
    public static Player[] getPlayers(String fileName){
        String f = new File("").getAbsolutePath();
        try{
            JsonReader file = new JsonReader(new FileReader(f.concat("/src/Assets/Players/" + fileName + ".json")));
            Gson gson = new Gson();
            Player[] players = gson.fromJson(file, Player[].class);
            return players;
        } catch (FileNotFoundException e) {
            Output.print("Couldn't find \"" + f + "\". Exiting.", "red");
            System.exit(0);
        }
        return null;
    }
}
