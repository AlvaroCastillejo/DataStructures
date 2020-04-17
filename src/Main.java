import Database.Connection;
import Database.Room;
import Model.Dijkstra.*;
import Utils.JsonUtils;
import Utils.LinkedList.LinkedList;
import Utils.Logic;
import Utils.Output;
import Utils.ScannerInput;

public class Main {
    public static void main(String[] args) {
        //Database fill-up
        Connection[] connections = JsonUtils.getConnections("ConnectionS");
        Room[] rooms = JsonUtils.getRooms("RoomS");

        double[][] adjacencyMatrix = null;
        try {
            adjacencyMatrix = new double[rooms.length][rooms.length];
        } catch (NullPointerException exception){
            Output.print("Unknown error. Something to do with rooms file.", "red");
        }

        Logic.initializeAdjacencyMatrix(adjacencyMatrix, rooms, connections);




        Room initialRoom = null;
        do {
            System.out.println("Select initial room: ");
            int iRoomID = ScannerInput.askInteger();
            initialRoom = Logic.getRoomByID(iRoomID, rooms);
            if(initialRoom != null){
                break;
            }
            Output.print("Room not found. Try again.", "red");
        } while (true);

        Room finalRoom = null;
        do {
            System.out.println("Select final room: ");
            int fRoomID = ScannerInput.askInteger();
            finalRoom = Logic.getRoomByID(fRoomID, rooms);
            if (finalRoom != null) {
                break;
            }
            Output.print("Room not found. Try again.", "red");
        } while (true);



        Room[] path = Dijkstra.dijkstra(adjacencyMatrix, initialRoom, finalRoom, rooms);
        Logic.printSolution(path);
    }
}
