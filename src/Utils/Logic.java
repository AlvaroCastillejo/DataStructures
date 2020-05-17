package Utils;

import Database.Connection;
import Database.Coordinate;
import Database.Room;
import Utils.LinkedList.LinkedListCustom;
import Utils.LinkedList.Node;

import java.util.LinkedList;

public class Logic {
    public static Room getRoomByID(int iRoomID, Room[] rooms) {
        for (Room room : rooms) {
            if (room.getId().equals(iRoomID)) {
                return room;
            }
        }
        return null;
    }

    public static Room[] getAdjacents(Room current, double[][] adjacencyMatrix, Room[] rooms) {
        LinkedListCustom<Room> adjacents = new LinkedListCustom<>();

        for(int i = 0; i < adjacencyMatrix[current.getId()].length; i++){
            if(adjacencyMatrix[current.getId()][i] != -1){
                adjacents.add(Logic.getRoomByID(i, rooms));
            }
        }

        return Logic.linkedListToArray(adjacents);
    }

    private static Room[] linkedListToArray(LinkedListCustom<Room> adjacents) {
        Room[] rooms = new Room[adjacents.size()];

        for (int i = 0; i < adjacents.size(); i++) {
            Room room = (Room)adjacents.get(i);
            rooms[i] = room;
        }

        return rooms;
    }

    public static Room[] updateC(Room[] c, Room adj, Room current) {
        c[adj.getId()] = current;
        return c;
    }

    public static void initializeAdjacencyMatrix(double[][] adjacencyMatrix, Room[] rooms, Connection[] connections) {
        for (Room room : rooms) {
            for(int i = 0; i < rooms.length; i++){
                adjacencyMatrix[room.getId()][i] = Logic.getConnectionValue(room.getId(), i, rooms, connections);
            }
        }
    }

    public static void initializeAdjacencyList(LinkedListCustom<LinkedListCustom<Room>> adjacencyList, Room[] rooms, Connection[] connections) {
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

    public static void fillC(Room[] c, Room[] rooms) {
        for (Room room : rooms) {
            c[room.getId()] = room;
        }
    }

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

    public static void setRoomVisited(Room current, Room[] rooms) {
        for (Room room : rooms) {
            if (room.getId().equals(current.getId())) {
                room.setVisited(true);
            }
        }
    }

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

    private static int lastNotNull(Room[] solution) {
        for(int i = 0; i < solution.length; i++){
            if(solution[i] == null){
                return i-1;
            }
        }
        return solution.length;
    }

    private static Room[] reverseArray(Room[] array) {
        for(int i=0; i<array.length/2; i++){
            Room temp = array[i];
            array[i] = array[array.length -i -1];
            array[array.length -i -1] = temp;
        }
        return array;
    }

    public static void printSolution(Room[] path) {
        for(Room room : path){
            if(room == null){
                continue;
            }
            Output.print(room.getRoomName(), "green");
        }
    }

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

    public static Room[] getAdjacentsFromAdjacencyList(Room current, LinkedListCustom<LinkedListCustom<Room>> adjacencyMatrix, Room[] rooms) {
        LinkedListCustom<Room> adjacents = (LinkedListCustom<Room>)adjacencyMatrix.get(current.getId());

        return Logic.linkedListToArray(adjacents);
    }
}
