
package Database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Connection {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("connection_name")
    @Expose
    private String connectionName;
    @SerializedName("room_connected")
    @Expose
    private List<Integer> roomConnected = null;
    @SerializedName("enemy_probability")
    @Expose
    private Integer enemyProbability;

    private int serialized_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public List<Integer> getRoomConnected() {
        return roomConnected;
    }

    public void setRoomConnected(List<Integer> roomConnected) {
        this.roomConnected = roomConnected;
    }

    public Integer getEnemyProbability() {
        return enemyProbability;
    }

    public void setEnemyProbability(Integer enemyProbability) {
        this.enemyProbability = enemyProbability;
    }

    public int getSerialized_id() {
        return serialized_id;
    }

    public void setSerialized_id(int serialized_id) {
        this.serialized_id = serialized_id;
    }
}
