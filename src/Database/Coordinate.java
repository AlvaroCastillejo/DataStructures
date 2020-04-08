
package Database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coordinate {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("X1")
    @Expose
    private Integer x1;
    @SerializedName("Y1")
    @Expose
    private Integer y1;
    @SerializedName("X2")
    @Expose
    private Integer x2;
    @SerializedName("Y2")
    @Expose
    private Integer y2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getX1() {
        return x1;
    }

    public void setX1(Integer x1) {
        this.x1 = x1;
    }

    public Integer getY1() {
        return y1;
    }

    public void setY1(Integer y1) {
        this.y1 = y1;
    }

    public Integer getX2() {
        return x2;
    }

    public void setX2(Integer x2) {
        this.x2 = x2;
    }

    public Integer getY2() {
        return y2;
    }

    public void setY2(Integer y2) {
        this.y2 = y2;
    }

}
