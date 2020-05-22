
package Database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreObject {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Integer price;

    public StoreObject () {
        this.name = null;
        this.price = -1;
    }

    public StoreObject(String name) {
        this.name = name;

    }

    public StoreObject(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
