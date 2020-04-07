package Utils;

import Database.Connection;
import Database.Room;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonUtils {
    public static Connection[] getConnections(String fileName){
        String f = new File("").getAbsolutePath();
        try {
            JsonReader file = new JsonReader(new FileReader(f.concat("/src/assets/" + fileName + ".json")));
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
            JsonReader file = new JsonReader(new FileReader(f.concat("/src/Assets/" + fileName + ".json")));
            Gson gson = new Gson();
            Room[] rooms = gson.fromJson(file, Room[].class);
            return rooms;
        } catch (FileNotFoundException e) {
            Output.print("Couldn't find \"" + f + "\". Exiting.", "red");
            System.exit(0);
        }
        return null;
    }
}
