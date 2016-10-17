package kumeshw.poke.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import kumeshw.poke.R;
import kumeshw.poke.services.OnClickActivityInterface;
import kumeshw.poke.model.PokeResponse;
import kumeshw.poke.model.Type;

import android.content.Context;

/**
 * Pokedex for Android - Fetches and displays data on Generation 1 Pokemon from the Kanto Region.
 * <p>
 *     Adapter handles integration of Pokemon data with the RecyclerView elements
 * Created by kumeshw on 16/10/2016.
 */

public class LinearRecyclerAdapter extends RecyclerView.Adapter<LinearRecyclerAdapter.ViewHolder> {
    private List<PokeResponse> dataSet;
    private OnClickActivityInterface pokemonSelection;
    private Context context;




    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewType;
        public TextView textViewName;
        public ImageView imgViewFrontDefault;
        public RelativeLayout pokemonCardLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewType = (TextView) itemView.findViewById(R.id.textview_type);
            textViewName = (TextView) itemView.findViewById(R.id.textview_name);
            imgViewFrontDefault = (ImageView) itemView.findViewById(R.id.imageview_front_default);
            pokemonCardLayout = (RelativeLayout) itemView.findViewById(R.id.pokemon_card_layout);
        }
    }

    public LinearRecyclerAdapter(List<PokeResponse> dataSet, Context context, OnClickActivityInterface pokemonSelection){
        this.dataSet = dataSet;
        this.context = context;
        this.pokemonSelection = pokemonSelection;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_view_holder, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return  viewHolder;
    }


    /**
     * Sets up the RecyclerView elements on the main screen and listens for pokemon click selection
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Picasso.with(context).load(dataSet.get(position).sprites.getFrontDefault())
                .resize(96,96)
                .into(holder.imgViewFrontDefault);
        holder.textViewName.setText(dataSet.get(position).name.toUpperCase());

        final List<String> typesTempArray = new ArrayList<String>();
        for (Type type : dataSet.get(position).types) {
            typesTempArray.add(type.toString());
        }
        holder.textViewType.setText(typesTempArray.toString());

        //Capture selected pokemon - on click listener
        holder.pokemonCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pokemonSelection.onPokemonCardClicked(dataSet.get(position));
                System.out.println("***** User clicked on - " + dataSet.get(position).name.toUpperCase() + " *****");
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }



}
