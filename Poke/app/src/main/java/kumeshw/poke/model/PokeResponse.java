package kumeshw.poke.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Pokedex for Android - Fetches and displays data on Generation 1 Pokemon from the Kanto Region.
 * <p>
 *     Core Pokemon Object model used throughout the app for data storage and retrieval
 * Created by kumeshw on 13/10/2016.
 */
public class PokeResponse implements Comparable<PokeResponse>, Serializable {


    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("base_experience")
    public int baseExperience;

    @SerializedName("height")
    public int height;

    //isDefault;

    //order;

    @SerializedName("weight")
    public int weight;

    @SerializedName("abilities")
    public List<Ability> abilities = new ArrayList<Ability>();

    //forms

    //game_indices

    //held_items

    //location_area_encounters

    @SerializedName("moves")
    public List<Move> moves = new ArrayList<Move>();

    //species;

    @SerializedName("sprites")
    public Sprites sprites;

    @SerializedName("stats")
    public List<Stat> stats = new ArrayList<Stat>();

    @SerializedName("types")
    public List<Type> types = new ArrayList<Type>();





    public PokeResponse(int id, String name, int baseExperience, int height, int weight,
                        List<Ability> abilities,  Sprites sprites,
                        List<Stat> stats, List<Move> moves, List<Type> types) {

        this.id = id;
        this.name = name;
        this.baseExperience = baseExperience;
        this.height = height;
        this.weight = weight;
        this.abilities = abilities;
        this.sprites = sprites;
        this.stats = stats;
        this.moves = moves;
        this.types = types;
    }



    /**
     * toString method for Pokemon data object
     */
    @Override
    public String toString() {
        return "PokeResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", baseExperience=" + baseExperience +
                ", height=" + height +
                ", weight=" + weight +
                ", abilities=" + abilities +
                ", sprites=" + sprites +
                ", stats=" + stats +
                ", moves=" + moves +
                ", types=" + types +
                '}' + "\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public int compareTo(PokeResponse idSortedPokeResponse) {
        int idToCompare = idSortedPokeResponse.getId();

        return this.id - idToCompare;
    }
}