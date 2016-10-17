package kumeshw.poke.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Pokedex for Android - Fetches and displays data on Generation 1 Pokemon from the Kanto Region.
 * <p>
 * Created by kumeshw on 13/10/2016.
 */
public class Stat implements Serializable {

    @SerializedName("base_stat")
    @Expose
    private Integer baseStat;
    @SerializedName("effort")
    @Expose
    private Integer effort;
    @SerializedName("stat")
    @Expose
    private StatList stat;

    public String toString(){
        return this.stat.getName();
    }


    public Integer getBaseStat() {
        return baseStat;
    }


    public void setBaseStat(Integer baseStat) {
        this.baseStat = baseStat;
    }


    public Integer getEffort() {
        return effort;
    }


    public void setEffort(Integer effort) {
        this.effort = effort;
    }


    public StatList getStat() {
        return stat;
    }


    public void setStat(StatList stat) {
        this.stat = stat;
    }

}