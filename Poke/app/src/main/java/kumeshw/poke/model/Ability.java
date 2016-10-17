package kumeshw.poke.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Pokedex for Android - Fetches and displays data on Generation 1 Pokemon from the Kanto Region.
 * <p>
 * Created by kumeshw on 13/10/2016.
 */
public class Ability implements Serializable{

    @SerializedName("is_hidden")
    @Expose
    private Boolean isHidden;
    @SerializedName("slot")
    @Expose
    private Integer slot;
    @SerializedName("ability")
    @Expose
    private AbilityList ability;

    public String toString(){
        return this.ability.getName();
    }



    public Boolean getIsHidden() {
        return isHidden;
    }


    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }


    public Integer getSlot() {
        return slot;
    }


    public void setSlot(Integer slot) {
        this.slot = slot;
    }


    public AbilityList getAbility() {
        return ability;
    }


    public void setAbility(AbilityList ability) {
        this.ability = ability;
    }


}
