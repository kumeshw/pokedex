package kumeshw.poke.model;

import java.io.Serializable;
import java.util.List;


import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Pokedex for Android - Fetches and displays data on Generation 1 Pokemon from the Kanto Region.
 * <p>
 * Created by kumeshw on 13/10/2016.
 */
public class Move implements Serializable {

    @SerializedName("move")
    @Expose
    private MoveList move;



    public String toString(){
        return this.move.getName();
    }


    public MoveList getMove() {
        return move;
    }


    public void setMove(MoveList move) {
        this.move = move;
    }



}
