import Database.Connection;
import Database.Room;
import Model.Dijkstra.*;
import Utils.JsonUtils;
import Utils.LinkedList.LinkedListCustom;
import Utils.Logic;
import Utils.Output;
import Utils.ScannerInput;

public class Main {
    public static void main(String[] args) {
        //Database fill-up
        Connection[] connections = JsonUtils.getConnections("ConnectionS");
        Room[] rooms = JsonUtils.getRooms("RoomS");

        //Only for computational cost tests.
        /*LinkedListCustom<Room> linkedListCustomRooms = new LinkedListCustom<>();
        linkedListCustomRooms.fill(rooms);

        LinkedListCustom<Connection> linkedListCustomConnections = new LinkedListCustom<>();
        linkedListCustomConnections.fill(connections);*/

        /*double[][] adjacencyMatrix = null;
        try {
            adjacencyMatrix = new double[rooms.length][rooms.length];
        } catch (NullPointerException exception){
            Output.print("Unknown error. Something to do with rooms file.", "red");
        }

        Logic.initializeAdjacencyMatrix(adjacencyMatrix, rooms, connections);
        */

        LinkedListCustom<LinkedListCustom<Room>> adjacencyList = new LinkedListCustom<>();
        Logic.initializeAdjacencyList(adjacencyList, rooms, connections);

        adjacencyList.print("green");
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

        //Dijkstra using adjacencyList.
        Room[] path = DijkstraAdjacencyList.dijkstra(adjacencyList, initialRoom, finalRoom, rooms);
        Logic.printSolution(path);
        //Dijkstra using adjacencyMatrix. Only for computational cost tests.
        //Room[] path = Dijkstra.dijkstra(adjacencyMatrix, initialRoom, finalRoom, rooms);
        //Logic.printSolution(path);
    }
}
