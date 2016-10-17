package kumeshw.poke.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Pokedex for Android - Fetches and displays data on Generation 1 Pokemon from the Kanto Region.
 * <p>
 * Created by kumeshw on 13/10/2016.
 */
public class Type implements Serializable {

    @SerializedName("slot")
    @Expose
    private Integer slot;
    @SerializedName("type")
    @Expose
    private TypeList type;

    public String toString(){
        return this.type.getName();
    }


    public Integer getSlot() {
        return slot;
    }


    public void setSlot(Integer slot) {
        this.slot = slot;
    }


    public TypeList getType() {
        return type;
    }


    public void setType(TypeList type) {
        this.type = type;
    }

}
