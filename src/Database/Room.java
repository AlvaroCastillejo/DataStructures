
package Database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Room {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("room_name")
    @Expose
    private String roomName;

    private boolean visited = false;
    private double connectionValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getSerialized_id() {
        return id;
    }

    public void setGoToCost(double connectionValue) {
        this.connectionValue = connectionValue;
    }

    public double getConnectionValue() {
        return connectionValue;
    }
}
