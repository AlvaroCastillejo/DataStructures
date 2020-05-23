package Utils;

import Database.Connection;
import Database.Coordinate;
import Database.Room;
import Utils.LinkedList.LinkedListCustom;
import Utils.LinkedList.Node;

import java.util.LinkedList;

public class Logic {
    /**
     * Get a room by its ID.
     * @param iRoomID The ID of the desired room.
     * @param rooms The array containing all the rooms.
     * @return The desired Room.
     */
    public static Room getRoomByID(int iRoomID, Room[] rooms) {
        for (Room room : rooms) {
            if (room.getId().equals(iRoomID)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Get the rooms that are connected to a given room.
     * @param current The room we are looking for its connected rooms.
     * @param adjacencyMatrix The matrix containing the information of the rooms.
     * @param rooms All the rooms.
     * @return An array containing all the rooms connected to de desired one.
     */
    public static Room[] getAdjacents(Room current, double[][] adjacencyMatrix, Room[] rooms) {
        LinkedListCustom<Room> adjacents = new LinkedListCustom<>();

        for(int i = 0; i < adjacencyMatrix[current.getId()].length; i++){
            if(adjacencyMatrix[current.getId()][i] != -1){
                adjacents.add(Logic.getRoomByID(i, rooms));
            }
        }

        return Logic.linkedListToArray(adjacents);
    }

    /**
     * Function that casts a LinkedList to Array.
     * @param adjacents LinkedList to be casted.
     * @return The array casted.
     */
    private static Room[] linkedListToArray(LinkedListCustom<Room> adjacents) {
        Room[] rooms = new Room[adjacents.size()];

        for (int i = 0; i < adjacents.size(); i++) {
            Room room = (Room)adjacents.get(i);
            rooms[i] = room;
        }

        return rooms;
    }

    /**
     * Function that returns the path to the final room updated.
     * @param c Array that is the current path.
     * @param adj New room to add.
     * @param current Room we are in.
     * @return Array of rooms updated.
     */
    public static Room[] updateC(Room[] c, Room adj, Room current) {
        c[adj.getId()] = current;
        return c;
    }

    /**
     * Initializes the database for the adjacency matrix.
     * @param adjacencyMatrix The matrix to fill.
     * @param rooms All the rooms.
     * @param connections All the connections.
     */
    public static void initializeAdjacencyMatrix(double[][] adjacencyMatrix, Room[] rooms, Connection[] connections) {
        //  _a___b___c_
        //a|_0_|_1_|_0_|
        //b|_1_|_0_|_1_|
        //c|_0_|_1_|_0_|
        //
        for (Room room : rooms) {
            for(int i = 0; i < rooms.length; i++){
                adjacencyMatrix[room.getId()][i] = Logic.getConnectionValue(room.getId(), i, rooms, connections);
            }
        }
    }

    /**
     * Initializes the database for the adjacency list.
     * @param adjacencyList The list to fill.
     * @param rooms All the rooms.
     * @param connections All the connections.
     */
    public static void initializeAdjacencyList(LinkedListCustom<LinkedListCustom<Room>> adjacencyList, Room[] rooms, Connection[] connections) {
        //
        //a:b
        //b:a,c
        //c:b
        //
        for(Room room : rooms){
            LinkedListCustom<Room> connectedRooms = new LinkedListCustom<>();
            for(Room innerRoom : rooms){
                double connectionValue = Logic.getConnectionValue(room.getId(), innerRoom.getId(), rooms, connections);
                if(connectionValue != -1){
                    Room newRoom = new Room();
                    newRoom.setVisited(false);
                    newRoom.setGoToCost(connectionValue);
                    newRoom.setRoomName(innerRoom.getRoomName());
                    newRoom.setId(innerRoom.getId());

                    connectedRooms.add(newRoom);
                }
            }
            connectedRooms.setName(room.getRoomName());
            adjacencyList.add(connectedRooms);
        }
    }

    /**
     * Gets the connection values between two given rooms.
     * @param roomA First room.
     * @param roomB Second room.
     * @param rooms All the rooms.
     * @param connections All the connections.
     * @return The value of the connection between.
     */
    private static double getConnectionValue(int roomA, int roomB, Room[] rooms, Connection[] connections) {
        if(roomA == roomB){
            return -1;
        }
        for(int i = 0; i < connections.length; i++){
            if(connections[i].getRoomConnected().contains(roomA) && connections[i].getRoomConnected().contains(roomB)){
                return connections[i].getEnemyProbability();
            }
        }
        return -1;
    }

    /**
     * Method that inserts rooms into empty array by id.
     * @param c Empty array of rooms.
     * @param rooms Array of rooms from database.
     */
    public static void fillC(Room[] c, Room[] rooms) {
        for (Room room : rooms) {
            c[room.getId()] = room;
        }
    }

    /**
     * Function that finds the room of q with less weight consulting d.
     * @param q Array of rooms from database.
     * @param d Array that contains the weight of every room.
     * @return The room found that has less cost to visit.
     */
    public static Room findMinimumWeightRoom(Room[] q, double[] d) {
        double min = Double.MAX_VALUE;
        int index = -1;
        for(int i = 0; i < d.length; i++){
            if(d[i] < min && !q[i].isVisited()){
                min = d[i];
                index = i;
            }
        }
        return q[index];
    }

    /**
     * Method that set to visited a given room.
     * @param current Room visited.
     * @param rooms Array that contains the rooms from the database.
     */
    public static void setRoomVisited(Room current, Room[] rooms) {
        for (Room room : rooms) {
            if (room.getId().equals(current.getId())) {
                room.setVisited(true);
            }
        }
    }

    /**
     * Function that interprets the c array to get the path between initialRoom and finalRoom.
     * @param c Array with the path made by the Dijkstra code.
     * @param rooms Array of rooms.
     * @param initialRoom Room we started from.
     * @param finalRoom Last room we added to the path.
     * @return Array with the final path ready to easily understand.
     */
    public static Room[] getSolution(Room[] c, Room[] rooms, Room initialRoom, Room finalRoom) {
        Room[] solution = new Room[c.length];

        solution[0] = finalRoom;
        Room lastAdded = finalRoom;

        for(int i = 0 ; i < c.length; i++){
            if(lastAdded.getId().equals(initialRoom.getId())){
                break;
            }
            solution[lastNotNull(solution)+1] = c[lastAdded.getId()];
            lastAdded = c[lastAdded.getId()];
        }

        return Logic.reverseArray(solution);
    }

    /**
     * Gets the index of the given array where the containing slot is not null..
     * @param solution The array given.
     * @return The index.
     */
    private static int lastNotNull(Room[] solution) {
        for(int i = 0; i < solution.length; i++){
            if(solution[i] == null){
                return i-1;
            }
        }
        return solution.length;
    }

    /**
     * Reverses the given array. array:1,2,3,4 -Reversed-> array:4,3,2,1.
     * @param array The given array.
     * @return The array reversed.
     */
    private static Room[] reverseArray(Room[] array) {
        for(int i=0; i<array.length/2; i++){
            Room temp = array[i];
            array[i] = array[array.length -i -1];
            array[array.length -i -1] = temp;
        }
        return array;
    }

    /**
     * Prints the path of the solution.
     * @param path The path given.
     */
    public static void printSolution(Room[] path) {
        for(Room room : path){
            if(room == null){
                continue;
            }
            Output.print(room.getRoomName(), "green");
        }
    }

    /**
     * Function that standardizes the coordinates when given in other format. For instance this case:
     * Original coordinates: (200,100),(100,50). The given coordinates are the left bottom and the right top corner.
     * The format we want is the opposite. (100,50),(200,100).
     * @param coordinates The translated coordinates.
     */
    public static void redefineCoordinates(Coordinate[] coordinates) {
        for(Coordinate i : coordinates){
            int minX = Math.min(i.getX1(), i.getX2());
            int minY = Math.min(i.getY1(), i.getY2());
            int maxX = Math.max(i.getX1(), i.getX2());
            int maxY = Math.max(i.getY1(), i.getY2());
            i.setX1(minX);
            i.setY1(minY);
            i.setX2(maxX);
            i.setY2(maxY);
        }
    }

    /**
     * Calculates the distance between two given points.
     * @param a First point.
     * @param b Second point.
     * @return The distance.
     */
    public static double distanceBetween(Coordinate a, Coordinate b) {
        int xA = (a.getX2()-a.getX1())/2;
        int yA = (a.getY2()-a.getY1())/2;

        int xB = (b.getX2()-b.getX1())/2;
        int yB = (b.getY2()-b.getY1())/2;

        xA += a.getX1();
        yA += a.getY1();
        xB += b.getX1();
        yB += b.getY1();

        return Math.sqrt((yB - yA) * (yB - yA) + (xB - xA) * (xB - xA));
    }

    /**
     * Function that returns all the rooms that are connected to a given one.
     * @param current Room given to get the adjacents from.
     * @param adjacencyMatrix Is list that contains every connection of every room.
     * @param rooms Unused for this version.
     * @return Array of rooms connected to the given one.
     */
    public static Room[] getAdjacentsFromAdjacencyList(Room current, LinkedListCustom<LinkedListCustom<Room>> adjacencyMatrix, Room[] rooms) {
        LinkedListCustom<Room> adjacents = (LinkedListCustom<Room>)adjacencyMatrix.get(current.getId());

        return Logic.linkedListToArray(adjacents);
    }
}
